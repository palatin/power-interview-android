package example.com.powerinterview.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.ConditionsAdapter;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.model.Condition;
import example.com.powerinterview.model.ConditionBlock;

/**
 * Created by Игорь on 17.04.2017.
 */




public class ConditionDialog extends DialogFragment {

    public static interface OnCompleteCondition {
        void onCompleteCondition(ConditionBlock conditionBlock);
    }

    @BindView(R.id.conditionRecyclerView)
    RecyclerView conditionRecyclerView;

    @BindView(R.id.doneButton)
    Button doneButton;

    @BindView(R.id.addCondition)
    Button addCondition;

    private OnCompleteCondition listener;

    private ConditionBlock condition;

    private List<Condition> conditions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnCompleteCondition) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_condition);


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT);




        ButterKnife.bind(this, dialog);


        conditions = new ArrayList<>();

        try {
            condition = getArguments().getParcelable("condition");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        if(condition == null)
            dismiss();
        if(condition.getConditions() != null)
            conditions.addAll(condition.getConditions());

        final ConditionsAdapter adapter = new ConditionsAdapter(conditions);

        conditionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        conditionRecyclerView.setAdapter(adapter);

        addCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditions.add(new Condition());
                adapter.notifyItemInserted(conditions.size() - 1);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condition.setConditions(conditions);
                listener.onCompleteCondition(condition);
                dismiss();
            }
        });

        return dialog;
    }


}
