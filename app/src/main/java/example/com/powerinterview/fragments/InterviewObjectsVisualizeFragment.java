package example.com.powerinterview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.InterviewObjectsAdapter;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.model.InterviewObject;

/**
 * Created by Игорь on 11.04.2017.
 */

public class InterviewObjectsVisualizeFragment extends Fragment {

    public static InterviewObjectsVisualizeFragment newInstance(ArrayList<InterviewObject> interviewObjects){
        InterviewObjectsVisualizeFragment interviewObjectsVisualizeFragment = new InterviewObjectsVisualizeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("interview", interviewObjects);
        interviewObjectsVisualizeFragment.setArguments(bundle);
        return interviewObjectsVisualizeFragment;
    }

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmet_visualizer, container, false);

        unbinder = ButterKnife.bind(this, view);





        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
