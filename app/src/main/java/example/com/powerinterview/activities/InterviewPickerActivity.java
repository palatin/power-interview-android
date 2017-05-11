package example.com.powerinterview.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.InterviewsAdapter;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.ConvertException;
import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.interfaces.InterviewPick;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.managers.InterviewsTemplatesManager;
import example.com.powerinterview.model.InterviewTemplate;
import example.com.powerinterview.network.InterviewClient;
import example.com.powerinterview.ui.CustomToast;
import example.com.powerinterview.utils.Converter;

public class InterviewPickerActivity extends BaseWorkerActivity implements InterviewPick {


    private InterviewClient client;
    private AccountManager accountManager;
    private List<InterviewTemplate> interviewTemplates;
    private InterviewsAdapter adapter;
    private Unbinder unbinder;

    private AlertDialog activationDialog;

    private InterviewsTemplatesManager interviewsTemplatesManager;


    @BindView(R.id.interviewsRecyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_picker);
        client = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewClient();
        interviewsTemplatesManager = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewManager();
        accountManager = ((PowerInterviewApp) getApplication()).getAuthComponent().accountManager();

        unbinder = ButterKnife.bind(this);

        final EditText codeText = new EditText(this);
        codeText.setMaxEms(20);
        codeText.setHint("Code");
        activationDialog = new AlertDialog.Builder(this)
                .setMessage("Enter secret code to access private templates")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activateCode(codeText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        activationDialog.setView(codeText);
        loadInterviews();
    }

    private void loadInterviews() {

        interviewTemplates = new ArrayList<>();
        adapter = new InterviewsAdapter(interviewTemplates, this, recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            client.getInterviewsModules(accountManager.getToken(), new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    writeDebugLog("Interview loader", "list of interviews loaded " + response.toString());


                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject moduleJSON = response.getJSONObject(i);
                            InterviewTemplate module = new InterviewTemplate();
                            module.setId(moduleJSON.getLong("id"));
                            module.setName(moduleJSON.getString("name"));
                            module.setAuthor(moduleJSON.getString("author"));
                            module.setDescription(moduleJSON.getString("description"));
                            interviewTemplates.add(module);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    if(interviewTemplates.size() > 0)
                        adapter.notifyItemRangeInserted(0, interviewTemplates.size());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    writeDebugLog("Interview loader", "exception from server, status code: " + statusCode + "message: " + responseString);

                }
            });
        } catch (EncryptionException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onInterviewPicked(InterviewTemplate interviewTemplate) {

        try {
            File file = interviewsTemplatesManager.getFileTemplateById(interviewTemplate.getId(), getApplicationContext());
            Intent intent = new Intent(InterviewPickerActivity.this, InterviewActivity.class);
            intent.putExtra("template", file);
            intent.putExtra("id", interviewTemplate.getId());
            startActivity(intent);
            finish();
        } catch (NullPointerException | FileNotFoundException e) {
            loadTemplate(interviewTemplate);
        }
        



    }

    private void loadTemplate(final InterviewTemplate interviewTemplate) {

        try {

            showProgressDialog("Loading template....");
            client.getInterviewModule(accountManager.getToken(), String.valueOf(interviewTemplate.getId()), new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    for (Header header: headers) {
                        writeDebugLog("Interview loader", "Result form loading interview module: " + header.getName() + " " + header.getValue());
                        if(header.getName().equals("Content-Type")) {
                            if(header.getValue().equals("application/json")) {
                                try {
                                    displayResult(Converter.bytesToJSON(responseBody));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ConvertException e) {
                                    e.printStackTrace();
                                }
                                finally {
                                    dismissProgressDialog();
                                }
                            }
                            else if(header.getValue().equals("application/octet-stream")) {
                                try {
                                    interviewsTemplatesManager.storeInterviewTemplate(interviewTemplate, getApplicationContext(), responseBody);
                                    File file = interviewsTemplatesManager.getFileTemplateById(interviewTemplate.getId(), getApplicationContext());
                                    Intent intent = new Intent(InterviewPickerActivity.this, InterviewActivity.class);
                                    intent.putExtra("template", file);
                                    startActivity(intent);
                                    finish();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    dismissProgressDialog();
                                    showToast(e.getMessage(), CustomToast.ToastType.TOAST_ALERT);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    writeDebugLog("Interview loader", "exception from server, status code: " + statusCode);
                    dismissProgressDialog();
                    showToast("Server error", CustomToast.ToastType.TOAST_ALERT);
                }
            });
        } catch (EncryptionException e) {
            e.printStackTrace();
            dismissProgressDialog();
        }
    }

    private void activateCode(String code) {
        if(code.isEmpty())
            return;
        try {
            showProgressDialog("Activating the code....");
            client.activateCode(accountManager.getToken(), code, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    dismissProgressDialog();
                    try {
                        if(displayResult(Converter.bytesToJSON(responseBody)))
                            loadInterviews();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ConvertException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    dismissProgressDialog();
                    showToast("Server error", CustomToast.ToastType.TOAST_ALERT);
                    writeDebugLog("Interview loader", "exception from server, status code: " + statusCode);
                }
            });
        } catch (EncryptionException e) {
            e.printStackTrace();
            dismissProgressDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.interview_picker_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.activate_code:
                activationDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
