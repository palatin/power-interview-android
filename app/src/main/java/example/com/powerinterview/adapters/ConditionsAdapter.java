package example.com.powerinterview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import example.com.powerinterview.R;
import example.com.powerinterview.activities.EditConditionActivity;
import example.com.powerinterview.model.Condition;

/**
 * Created by Игорь on 17.04.2017.
 */

public class ConditionsAdapter extends RecyclerView.Adapter<ConditionsAdapter.ViewHolder> implements View.OnClickListener {

    private List<Condition> conditions;
    private RecyclerView recyclerView;
    private EditConditionListener listener;



    public ConditionsAdapter(List<Condition> conditions, RecyclerView recyclerView, EditConditionListener listener) {
        this.conditions = conditions;
        this.recyclerView = recyclerView;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View conditionView = inflater.inflate(R.layout.condition_list_item, parent, false);
        conditionView.setOnClickListener(this);
        final ViewHolder holder = new ViewHolder(conditionView);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Condition condition = conditions.get(position);

        holder.conditionTextView.setText(condition.toString());

    }

    @Override
    public int getItemCount() {
        return conditions.size();
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.indexOfChild(v);
        if (position != -1)
            listener.editCondition(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView conditionTextView;

        ViewHolder(View itemView) {

            super(itemView);

            conditionTextView = (TextView) itemView.findViewById(R.id.conditionTextView);
        }
    }

    public interface EditConditionListener {

        void editCondition(int index);
    }

}
