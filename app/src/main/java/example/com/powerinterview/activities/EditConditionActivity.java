package example.com.powerinterview.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.ConditionsAdapter;
import example.com.powerinterview.adapters.VariablesAdapter;
import example.com.powerinterview.dialogs.ConditionDialog;
import example.com.powerinterview.model.Condition;
import example.com.powerinterview.model.ConditionBlock;
import example.com.powerinterview.model.Variable;
import example.com.powerinterview.ui.CustomToast;

public class EditConditionActivity extends BaseWorkerActivity implements ConditionsAdapter.EditConditionListener, ConditionDialog.ConditionListener {

    @BindView(R.id.conditionsRecyclerView)
    RecyclerView conditionRecyclerView;

    private Unbinder unbinder;


    private List<Condition> conditions;
    private ConditionBlock condition;
    private HashMap<String, Variable> variables;
    private ConditionDialog dialog;

    private ConditionsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_condition);


        unbinder = ButterKnife.bind(this);

        Bundle bundle =  getIntent().getExtras();

        variables = (HashMap<String, Variable>) bundle.getSerializable("variables");

        condition = bundle.getParcelable("condition");

        conditions = condition.getConditions();

        adapter = new ConditionsAdapter(conditions, conditionRecyclerView, this);

        conditionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        conditionRecyclerView.setAdapter(adapter);

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
                removeCondition(viewHolder.getAdapterPosition());
            }
        });

        ith.attachToRecyclerView(conditionRecyclerView);

        dialog = new ConditionDialog();


    }

    @Override
    public void editCondition(int index) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("variables", variables);
        bundle.putParcelable("condition", conditions.get(index));
        bundle.putInt("index", index);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "condition_dialog");
    }

    private void removeCondition(int index) {
        conditions.remove(index);
        adapter.notifyItemRemoved(index);
    }

    @OnClick(R.id.addCondition)
    public void addCondition() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("variables", variables);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "condition_dialog");
    }

    @OnClick(R.id.cancelButton)
    public void onCancel() {
        finish();
    }

    @OnClick(R.id.doneButton)
    public void onDone() {

        condition.setConditions(conditions);
        Intent intent = new Intent();
        intent.putExtra("condition", (Parcelable) condition);
        setResult(RESULT_OK, intent);
        finish();
        finish();
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onConditionReady(Condition condition) {



        conditions.add(condition);
        adapter.notifyItemInserted(conditions.size());
    }

    @Override
    public void onConditionReady(Condition condition, int index) {

        conditions.set(index, condition);
        adapter.notifyItemChanged(index);
    }
}
