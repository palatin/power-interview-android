package example.com.powerinterview.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;
import example.com.powerinterview.R;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.dialogs.ConditionDialog;
import example.com.powerinterview.exceptions.ConvertException;
import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.fragments.InterviewObjectsFragment;
import example.com.powerinterview.fragments.InterviewObjectsVisualizeFragment;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.model.ConditionBlock;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.Variable;
import example.com.powerinterview.network.InterviewClient;
import example.com.powerinterview.ui.CustomToast;
import example.com.powerinterview.utils.Converter;
import example.com.powerinterview.utils.Encrypt;

public class ConstructorActivity extends BaseWorkerActivity implements IEditInterviewObjectListener {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Interview interview;
    private InterviewClient client;
    private AccountManager accountManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    ViewPager mViewPager;

    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constructor);

        unbinder = ButterKnife.bind(this);

        initInterview();



        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        client = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewClient();
        accountManager = ((PowerInterviewApp) getApplication()).getAuthComponent().accountManager();



    }



    private void initInterview() {
        interview = new Interview();
        interview.setInterviewObjects(new ArrayList<InterviewObject>());
        interview.setVariables(new HashMap<String, Variable>());

    }

    @Override
    public void editObject(InterviewObject object) {
        if(object instanceof Question) {
            Intent intent = new Intent(ConstructorActivity.this, EditQuestionActivity.class);
            intent.putExtra("question", (Parcelable) object);
            startActivityForResult(intent, 1);
        }
        if(object instanceof ConditionBlock) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("condition", object);
            HashMap<String, Variable> variableHashMap = interview.getVariables();
            bundle.putSerializable("variables", variableHashMap);
            Intent intent = new Intent(this, EditConditionActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 2);

        }
    }

    @Override
    public void manageVariables() {
        Intent intent = new Intent(this, ManageVariablesActivity.class);
        intent.putExtra("variables", interview.getVariables());
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Question question = data.getParcelableExtra("question");
                    interview.getInterviewObjects().set(interview.getInterviewObjects().indexOf(question), question);
                break;
                case 2:
                    ConditionBlock conditionBlock = data.getParcelableExtra("condition");
                    interview.getInterviewObjects().set(interview.getInterviewObjects().indexOf(conditionBlock), conditionBlock);
                    break;
                case 3:
                    HashMap<String, Variable> variables = (HashMap<String, Variable>) data.getSerializableExtra("variables");
                    interview.setVariables(variables);
                    break;
            }
        }
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return InterviewObjectsFragment.newInstance(interview);
            else
                return InterviewObjectsVisualizeFragment.newInstance(null);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Questions List";
                case 1:
                    return "Questions tree";
            }
            return null;
        }
    }

    @OnClick(R.id.addInterviewButton)
    public void addInterview() {

        final EditText interviewName = new EditText(this);
        interviewName.setMaxEms(20);
        interviewName.setHint("Interview name");

        final EditText description = new EditText(this);
        description.setMaxEms(120);
        description.setHint("Description, 120 characters max");

        final EditText password = new EditText(this);
        password.setMaxEms(20);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setHint("Password (not required)");

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(interviewName);
        linearLayout.addView(password);
        linearLayout.addView(description);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Create Interview")
                .setView(linearLayout)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        interview.setName(interviewName.getText().toString());
                        interview.setDescription(description.getText().toString());
                        interview.setPassword(password.getText().toString());
                        uploadInterview(interview);
                    }
                })
                .create();
        dialog.show();

    }

    private void uploadInterview(Interview interview) {

        try {
            showProgressDialog("Uploading interview....");

            String aesKey = Encrypt.generateRandomAESKey();

            try {
                client.storeInterviewModule(accountManager.getToken(), interview, Encrypt.encryptAES(interview.getInputStream(), aesKey), aesKey, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        dismissProgressDialog();

                        try {
                            JSONObject obj = Converter.bytesToJSON(responseBody);
                            if(obj.getBoolean("result")) {
                                showToast(getString(R.string.interview_template_uploaded), CustomToast.ToastType.TOAST_SUCCESS);
                                finish();
                            }
                            else
                            {
                                showToast(obj.getString("msg"), CustomToast.ToastType.TOAST_ALERT);
                                accountManager.removeAccount();
                            }
                        } catch (ConvertException | JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                            accountManager.removeAccount();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        dismissProgressDialog();
                        Log.v("Server error message", Arrays.toString(responseBody));
                        showToast("Server error", CustomToast.ToastType.TOAST_ALERT);
                    }
                });
            } catch (Exception e) {
                dismissProgressDialog();
                e.printStackTrace();
                showToast(getString(R.string.exception), CustomToast.ToastType.TOAST_ALERT);
            }
        } catch (EncryptionException e) {
            dismissProgressDialog();
            e.printStackTrace();
            showToast(getString(R.string.exception), CustomToast.ToastType.TOAST_ALERT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
