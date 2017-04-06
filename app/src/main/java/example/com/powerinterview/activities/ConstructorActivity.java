package example.com.powerinterview.activities;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.fragments.EditQuestionFragment;
import example.com.powerinterview.fragments.InterviewObjectsFragment;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Question;

public class ConstructorActivity extends AppCompatActivity implements IEditInterviewObjectListener{


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
        interviewObjects.add(question);
    }

    @Override
    public void editObject(InterviewObject object) {
        if(object instanceof Question) {

        }
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //return InterviewObjectsFragment.newInstance(interviewObjects);
            return EditQuestionFragment.newInstance(new Question());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
