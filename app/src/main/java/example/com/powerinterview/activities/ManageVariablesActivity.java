package example.com.powerinterview.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.VariablesAdapter;
import example.com.powerinterview.interfaces.EditVariableListener;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.Variable;
import example.com.powerinterview.ui.CustomToast;
import example.com.powerinterview.utils.VariablesUtil;

public class ManageVariablesActivity extends BaseWorkerActivity implements EditVariableListener {

    @BindView(R.id.variablesRecyclerView)
    RecyclerView variablesRecyclerView;

    private Unbinder unbinder;

    private HashMap<String, Variable> variables;
    private List<String> variableNames;

    private VariablesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_variables);

        unbinder = ButterKnife.bind(this);

        variables = (HashMap<String, Variable>)getIntent().getSerializableExtra("variables");

        variableNames = new ArrayList<>(variables.size());



        for (String key: variables.keySet()) {
            variableNames.add(key);
        }


        adapter = new VariablesAdapter(variableNames, this, variablesRecyclerView);

        variablesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        variablesRecyclerView.setAdapter(adapter);

        ItemTouchHelper ith = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                onVariableRemove(viewHolder.getAdapterPosition());
            }
        });

        ith.attachToRecyclerView(variablesRecyclerView);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onVariableEdit(final int index) {

        final String oldName = variableNames.get(index);

        final Variable variable = variables.get(oldName);


        createVariableDialog(oldName, variable, new EditVariableAction() {
            @Override
            public void editVariable(String name, String value) {


                variable.setValue(value);

                if(!oldName.equals(name) && checkVariableName(name)) {
                    variables.remove(oldName);
                    variables.put(name, VariablesUtil.parseVariableToExpectType(variable));
                    variableNames.set(index, name);
                    adapter.notifyItemChanged(index);
                }
                else {
                    variables.put(name, VariablesUtil.parseVariableToExpectType(variable));
                }

            }
        });
    }

    private void createVariableDialog(final String name, final Variable variable, final EditVariableAction variableActionListener) {

        final EditText variableNameEditText = new EditText(this);
        variableNameEditText.setMaxEms(20);
        variableNameEditText.setHint("Variable name");
        variableNameEditText.setText(name);

        final EditText variableValueEditText = new EditText(this);
        variableValueEditText.setMaxEms(120);
        variableValueEditText.setHint("Variable default value");
        variableValueEditText.setText(variable.getValue().toString());


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(variableNameEditText);
        linearLayout.addView(variableValueEditText);


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Edit variable")
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
                        dialog.dismiss();
                        variableActionListener.editVariable(variableNameEditText.getText().toString(), variableValueEditText.getText().toString());
                    }
                }).create();

        dialog.show();
    }


    @Override
    public void onVariableRemove(int index) {
        String key = variableNames.remove(index);
        variables.remove(key);
        adapter.notifyItemRemoved(index);
    }

    private boolean checkVariableName(String name) {
        if(name.isEmpty()) {
            showToast("Variable name can't be empty", CustomToast.ToastType.TOAST_ALERT);
            return true;
        }
        if(variables.containsKey(name)) {
            showToast("Variable with name - " + name + " already exists", CustomToast.ToastType.TOAST_ALERT);
            return false;
        }
        return true;
    }


    @OnClick(R.id.addVariable)
    public void onAddVariableClick() {

        final Variable variable = new Variable("");
        createVariableDialog("", variable, new EditVariableAction() {
            @Override
            public void editVariable(String name, String value) {
                if(!checkVariableName(name))
                    return;

                variable.setValue(value);
                variables.put(name, VariablesUtil.parseVariableToExpectType(variable));

                variableNames.add(name);
                adapter.notifyItemInserted(variableNames.size() - 1);
            }
        });

    }

    @OnClick(R.id.doneButton)
    public void onDoneClick() {
        Intent intent = new Intent();
        intent.putExtra("variables", (Serializable) variables);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.cancelButton)
    public void onCancelClick() {
        finish();
    }


    private interface EditVariableAction {
        void editVariable(String name, String value);
    }
}
