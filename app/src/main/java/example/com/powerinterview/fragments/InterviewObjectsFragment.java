package example.com.powerinterview.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.InterviewObjectsAdapter;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.model.ConditionBlock;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.Variable;


/**
 * Created by Игорь on 01.04.2017.
 */

public class InterviewObjectsFragment extends Fragment {


    private Unbinder unbinder;
    private InterviewObjectsAdapter adapter;
    private Interview interview;



    public static InterviewObjectsFragment newInstance(Interview interview){
        InterviewObjectsFragment interviewObjectsFragment = new InterviewObjectsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("interview", interview);
        interviewObjectsFragment.setArguments(bundle);
        return interviewObjectsFragment;
    }




    @BindView(R.id.questionsRecyclerView)
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_questions_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        try {
            interview = getArguments().getParcelable("interview");
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }

        if(interview != null && getActivity() instanceof IEditInterviewObjectListener) {
            adapter = new InterviewObjectsAdapter(interview.getInterviewObjects(), (IEditInterviewObjectListener) getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.constructor_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_question:
                addQuestion();
                break;
            case R.id.add_condition:
                addCondition();
                break;
            case R.id.manage_variables:
                manageVariables();
                break;
        }
        return false;
    }

    private void addQuestion() {
        List<InterviewObject> interviewObjects = interview.getInterviewObjects();
        Question question = new Question();
        question.setId(interviewObjects.size());
        interviewObjects.add(question);
        adapter.notifyItemInserted(interviewObjects.size() - 1);
    }


    private void addCondition() {
        List<InterviewObject> interviewObjects = interview.getInterviewObjects();
        ConditionBlock conditionBlock = new ConditionBlock();
        conditionBlock.setId(interviewObjects.size());
        interviewObjects.add(conditionBlock);
        adapter.notifyItemInserted(interviewObjects.size() - 1);
    }

    private void manageVariables() {
        ((IEditInterviewObjectListener) getActivity()).manageVariables();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
