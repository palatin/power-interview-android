package example.com.powerinterview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.InterviewObjectsAdapter;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.interfaces.ICustomizableWidget;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.interfaces.IWidgetsProvider;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Question;

/**
 * Created by Игорь on 01.04.2017.
 */

public class EditQuestionFragment extends Fragment {


    @Inject
    IWidgetsProvider widgetsProvider;

    private Unbinder unbinder;
    private Question question;

    @BindView(R.id.widgetsLayout)
    LinearLayout widgetsLayout;

    @BindView(R.id.questionView)
    LinearLayout questionView;

    public static EditQuestionFragment newInstance(Question object){
        EditQuestionFragment editQuestionFragment = new EditQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("question", object);
        editQuestionFragment.setArguments(bundle);
        return editQuestionFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_question, container, false);


        unbinder = ButterKnife.bind(this, view);

        ((PowerInterviewApp) getActivity().getApplication()).getInterviewComponent().inject(this);

        try {
            question = getArguments().getParcelable("question");
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }

        if(question != null && getActivity() instanceof IEditInterviewObjectListener) {

        }

        initWidgets();

        return view;
    }

    private void initWidgets() {

        final View.OnClickListener customizeWidget = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ICustomizableWidget) v).customize();
            }
        };


        View.OnClickListener createWidget = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    View view = widgetsProvider.getEditableSpecificWidget(v, getContext());
                    if(view instanceof ICustomizableWidget) {
                        view.setOnClickListener(customizeWidget);
                    }
                    questionView.addView(view);
                } catch (FactoryException e) {
                    e.printStackTrace();
                }
            }
        };


        try {
            View[] views = widgetsProvider.getWidgets(getContext());
            for (View view: views) {
                //TODO add visitor pattern
                view.setOnClickListener(createWidget);
                widgetsLayout.addView(view);
            }
        } catch (FactoryException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
