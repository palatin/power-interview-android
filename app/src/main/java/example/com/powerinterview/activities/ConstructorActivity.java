package example.com.powerinterview.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.dialogs.ConditionDialog;
import example.com.powerinterview.fragments.InterviewObjectsFragment;
import example.com.powerinterview.fragments.InterviewObjectsVisualizeFragment;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.model.Condition;
import example.com.powerinterview.model.ConditionBlock;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.Widget;
import example.com.powerinterview.ui.PIBootstrapButton;

public class ConstructorActivity extends AppCompatActivity implements IEditInterviewObjectListener, ConditionDialog.OnCompleteCondition {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ArrayList<InterviewObject> interviewObjects;

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



    }



    private void initInterview() {
        interviewObjects = new ArrayList<>();
        Question question = new Question();
        question.setId(1);
        List<Widget> widgets = new ArrayList<>();
        Widget widget = new Widget();
        widget.setClassName(PIBootstrapButton.class.getName());
        widgets.add(widget);
        question.setWidgets(widgets);
        interviewObjects.add(question);
    }

    @Override
    public void editObject(InterviewObject object) {
        if(object instanceof Question) {
            Intent intent = new Intent(ConstructorActivity.this, EditQuestionActivity.class);
            intent.putExtra("question", object);
            startActivityForResult(intent, 1);
        }
        if(object instanceof ConditionBlock) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("condition", object);
            ConditionDialog dialog = new ConditionDialog();
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "condition_dialog");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Question question = data.getParcelableExtra("question");
                    interviewObjects.set(interviewObjects.indexOf(question), question);
                break;
            }
        }
    }

    @Override
    public void onCompleteCondition(ConditionBlock conditionBlock) {
        interviewObjects.set(interviewObjects.indexOf(conditionBlock), conditionBlock);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return InterviewObjectsFragment.newInstance(interviewObjects);
            else
                return InterviewObjectsVisualizeFragment.newInstance(interviewObjects);
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
