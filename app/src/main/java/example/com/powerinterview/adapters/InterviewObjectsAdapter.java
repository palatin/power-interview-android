package example.com.powerinterview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;


import java.util.List;

import example.com.powerinterview.R;
import example.com.powerinterview.interfaces.IEditInterviewObjectListener;
import example.com.powerinterview.model.InterviewObject;

/**
 * Created by Игорь on 25.02.2017.
 */

public class InterviewObjectsAdapter extends RecyclerView.Adapter<InterviewObjectsAdapter.ViewHolder> implements View.OnClickListener {


    private List<InterviewObject> interviewObjects;
    private IEditInterviewObjectListener listener;


    public InterviewObjectsAdapter(List<InterviewObject> interviewObjects, IEditInterviewObjectListener listener) {
        this.interviewObjects = interviewObjects;
        this.listener = listener;
    }


    @Override
    public InterviewObjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View interviewObjectView = inflater.inflate(R.layout.question_item, parent, false);
        ViewHolder holder = new ViewHolder(interviewObjectView);
        holder.editButton.setOnClickListener(this);


        return holder;
    }



    @Override
    public void onBindViewHolder(InterviewObjectsAdapter.ViewHolder viewHolder, int position) {

        InterviewObject interviewObject = interviewObjects.get(position);


        viewHolder.titleTextView.setText(interviewObject.getTitle());

        viewHolder.editButton.setTag(position);
        viewHolder.deleteButton.setTag(position);
    }


    @Override
    public int getItemCount() {
        return interviewObjects.size();
    }



    @Override
    public void onClick(View view) {
        listener.editObject(interviewObjects.get((Integer) view.getTag()));
    }


    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView titleTextView;
        BootstrapButton editButton;
        BootstrapButton deleteButton;


        ViewHolder(View itemView) {

            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.objectTitle);
            editButton = (BootstrapButton) itemView.findViewById(R.id.editButton);
            deleteButton = (BootstrapButton) itemView.findViewById(R.id.deleteButton);
        }
    }



}
