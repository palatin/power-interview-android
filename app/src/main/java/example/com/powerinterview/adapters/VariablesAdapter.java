package example.com.powerinterview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.HashMap;
import java.util.List;

import example.com.powerinterview.R;
import example.com.powerinterview.interfaces.EditVariableListener;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.model.InterviewObject;
import example.com.powerinterview.model.Variable;

/**
 * Created by Игорь on 08.05.2017.
 */

public class VariablesAdapter extends RecyclerView.Adapter<VariablesAdapter.ViewHolder> implements View.OnClickListener {


    private List<String> variables;
    private EditVariableListener listener;
    private RecyclerView recyclerView;


    public VariablesAdapter(List<String> variables, EditVariableListener listener, RecyclerView recyclerView) {
        this.variables = variables;
        this.listener = listener;
        this.recyclerView = recyclerView;
    }


    @Override
    public VariablesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View variableView = inflater.inflate(R.layout.variable_list_item, parent, false);
        VariablesAdapter.ViewHolder holder = new VariablesAdapter.ViewHolder(variableView);
        variableView.setOnClickListener(this);


        return holder;
    }


    @Override
    public void onBindViewHolder(VariablesAdapter.ViewHolder viewHolder, int position) {

        String variableName = variables.get(position);


        viewHolder.variableTextView.setText(variableName);


    }


    @Override
    public int getItemCount() {
        return variables.size();
    }


    @Override
    public void onClick(View view) {
        int position = recyclerView.indexOfChild(view);
        if (position != -1)
            listener.onVariableEdit(position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView variableTextView;


        ViewHolder(View itemView) {

            super(itemView);

            variableTextView = (TextView) itemView.findViewById(R.id.variableName);
        }
    }
}