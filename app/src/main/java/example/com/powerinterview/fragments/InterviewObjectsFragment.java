package example.com.powerinterview.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.InterviewObjectsAdapter;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.model.InterviewObject;


/**
 * Created by Игорь on 01.04.2017.
 */

public class InterviewObjectsFragment extends Fragment {


    private Unbinder unbinder;
    private InterviewObjectsAdapter adapter;
    private ArrayList<InterviewObject> interviewObjects;

    public static InterviewObjectsFragment newInstance(ArrayList<InterviewObject> interviewObjects){
        InterviewObjectsFragment interviewObjectsFragment = new InterviewObjectsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("interview", interviewObjects);
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



        try {
            interviewObjects = getArguments().getParcelableArrayList("interview");
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }

        if(interviewObjects != null && getActivity() instanceof IEditInterviewObjectListener) {
            adapter = new InterviewObjectsAdapter(interviewObjects, (IEditInterviewObjectListener) getActivity());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }





        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
