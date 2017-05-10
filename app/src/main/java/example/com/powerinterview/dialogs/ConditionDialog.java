package example.com.powerinterview.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.model.Condition;
import example.com.powerinterview.model.Variable;
import example.com.powerinterview.ui.CustomToast;
import example.com.powerinterview.utils.VariablesUtil;

/**
 * Created by Игорь on 17.04.2017.
 */




public class ConditionDialog extends DialogFragment {


    @BindView(R.id.leftSideCondition)
    AutoCompleteTextView leftSideCondition;

    @BindView(R.id.rightSideCondition)
    AutoCompleteTextView rightSideCondition;

    @BindView(R.id.operandSpinner)
    Spinner operandSpinner;

    private Unbinder unbinder;

    private HashMap<String, Variable> variables;

    private List<String> possibleOperands;

    private ArrayAdapter<String> spinnerArrayAdapter;


    private String[] integerAndFloatOperands = new String[] {
            "=",
            "≠",
            ">",
            "<",
            "≥",
            "≤"
    };

    private String[] stringAndBooleanOperands = new String[] {
            "=",
            "≠"
    };

    private ConditionListener listener;

    private int updatableIndex = -1;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ConditionListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog_condition);


        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT);

        unbinder = ButterKnife.bind(this, dialog);

        variables = (HashMap<String, Variable>) getArguments().getSerializable("variables");

        possibleOperands = new ArrayList<>();

        spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, possibleOperands);
        operandSpinner.setAdapter(spinnerArrayAdapter);

        leftSideCondition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //if something changed on left side
                rightSideCondition.setText("");
                String text = leftSideCondition.getText().toString();
                if(!isVariableName(text)) {
                    //left side must contain variable
                    clearOperands();
                    leftSideCondition.setError("Left side of condition must contains variable");
                    return;
                }
                else {
                    updatePossibleOperands(text);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rightSideCondition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = rightSideCondition.getText().toString();
                if(!text.isEmpty() && !leftSideCondition.getText().toString().isEmpty()) {
                    if (isVariableName(text)) {
                        //if right side variable we need to compare types
                        if(!compareTwoVariablesType(text, leftSideCondition.getText().toString())) {
                            clearOperands();
                            rightSideCondition.setError("Variable on left side has another type");
                        }
                        else {
                            updatePossibleOperands(text);
                        }
                    }
                    else {
                        if(!compareVariableWithValue(leftSideCondition.getText().toString(), text)) {
                            clearOperands();
                            rightSideCondition.setError("Seems like left side variable and right side data have different formats");
                        }
                        else {
                            updatePossibleOperands(leftSideCondition.getText().toString());
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String[] variablesNames = new String[variables.size()];

        Iterator<Map.Entry<String, Variable>> it = variables.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry<String, Variable> pair = it.next();
            variablesNames[i] = pair.getKey();
            i++;
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, variablesNames);

        leftSideCondition.setAdapter(arrayAdapter);

        rightSideCondition.setAdapter(arrayAdapter);

        Condition condition = getArguments().getParcelable("condition");

        if(condition != null) {

            updatableIndex = getArguments().getInt("index", -1);

            leftSideCondition.setText(condition.getLeftSide());
            rightSideCondition.setText(condition.getRightSide());

            operandSpinner.setSelection(spinnerArrayAdapter.getPosition(condition.getOperand()));
        }

        return dialog;
    }

    private boolean compareTwoVariablesType(String firstVariableName, String secondVariableName) {
        return variables.get(firstVariableName).getType() == variables.get(secondVariableName).getType();
    }

    private boolean compareVariableWithValue(String variableName, String value) {
        Variable variableToCompare = new Variable(value);
        VariablesUtil.parseVariableToExpectType(variableToCompare);
        return variables.get(variableName).getType() == variableToCompare.getType();

    }

    private void clearOperands() {
        possibleOperands.clear();
        spinnerArrayAdapter.notifyDataSetChanged();
    }


    //here we determine possible operand value
    private void updatePossibleOperands(String variableName) {
        possibleOperands.clear();
        String[] operands = null;
        if(isVariableName(variableName)) {
            Variable variable = variables.get(variableName);

            if(variable.getType() == Variable.Type.Number) {
                operands = integerAndFloatOperands;

            }
            else {
                operands = stringAndBooleanOperands;
            }
        }

        for (String s: operands) {
            possibleOperands.add(s);
        }
        spinnerArrayAdapter.notifyDataSetChanged();
    }


    private boolean isVariableName(String name) {
        return variables.containsKey(name);
    }

    @OnClick(R.id.cancelButton)
    public void onCancel() {
        dismiss();
    }

    @OnClick(R.id.doneButton)
    public void onDone() {
        if(spinnerArrayAdapter.getCount() == 0) {
            new CustomToast(getContext(), "Please verify condition, something wrong", Toast.LENGTH_SHORT, CustomToast.ToastType.TOAST_ALERT).show();
            return;
        }
        if(leftSideCondition.getText().toString().isEmpty() || rightSideCondition.getText().toString().isEmpty()) {
            new CustomToast(getContext(), "Left and right conditions sides must be non empty", Toast.LENGTH_SHORT, CustomToast.ToastType.TOAST_ALERT).show();
            return;
        }

        Condition condition = new Condition();
        condition.setCondition(leftSideCondition.getText().toString() + "::" + operandSpinner.getSelectedItem().toString() + "::" + rightSideCondition.getText().toString());
        if(updatableIndex != -1)
            listener.onConditionReady(condition, updatableIndex);
        else
            listener.onConditionReady(condition);
        dismiss();
    }




    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        unbinder.unbind();
    }

    public interface ConditionListener {

        void onConditionReady(Condition condition);

        void onConditionReady(Condition condition, int index);


    }
}
