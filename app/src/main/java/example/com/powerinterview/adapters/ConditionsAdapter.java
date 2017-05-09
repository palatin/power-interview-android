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

import java.util.List;

import example.com.powerinterview.R;
import example.com.powerinterview.model.Condition;

/**
 * Created by Игорь on 17.04.2017.
 */

public class ConditionsAdapter extends RecyclerView.Adapter<ConditionsAdapter.ViewHolder> {

    private List<Condition> conditions;
    private ArrayAdapter<String> autoCompleteVariableName;


    public ConditionsAdapter(List<Condition> conditions, String[] variableNames, Context context) {
        this.conditions = conditions;
        autoCompleteVariableName = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, variableNames);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View conditionView = inflater.inflate(R.layout.condition_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(conditionView);

        holder.condition.setAdapter(autoCompleteVariableName);


        holder.condition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int position = holder.getAdapterPosition();
                Condition condition = conditions.get(position);
                condition.setCondition(s.toString());
                conditions.set(position, condition);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.goTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int position = holder.getAdapterPosition();
                Condition condition = conditions.get(position);
                condition.setGoTo(s.toString());
                conditions.set(position, condition);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Condition condition = conditions.get(position);

        holder.condition.setText(condition.getCondition());

        holder.goTo.setText(condition.getGoTo());

    }

    @Override
    public int getItemCount() {
        return conditions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        AutoCompleteTextView condition;
        EditText goTo;

        ViewHolder(View itemView) {

            super(itemView);

            condition = (AutoCompleteTextView) itemView.findViewById(R.id.condition);
            goTo = (EditText) itemView.findViewById(R.id.goTo);
        }
    }

}
