package example.com.powerinterview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.com.powerinterview.R;
import example.com.powerinterview.interfaces.InterviewPick;
import example.com.powerinterview.model.InterviewTemplate;

/**
 * Created by Игорь on 29.04.2017.
 */

public class InterviewsAdapter extends RecyclerView.Adapter<InterviewsAdapter.ViewHolder> implements View.OnClickListener {

    private List<InterviewTemplate> interviewTemplates;
    private InterviewPick interviewPick;
    private RecyclerView recyclerView;


    public InterviewsAdapter(List<InterviewTemplate> interviewTemplates, InterviewPick interviewPick, RecyclerView recyclerView) {
        this.interviewTemplates = interviewTemplates;
        this.interviewPick = interviewPick;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View interviewModuleView = inflater.inflate(R.layout.interview_list_item, parent, false);
        ViewHolder holder = new ViewHolder(interviewModuleView);

        interviewModuleView.setOnClickListener(this);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InterviewTemplate interviewTemplate = interviewTemplates.get(position);
        holder.interviewName.setText(interviewTemplate.getName());
        holder.interviewAuthor.setText("Created by " + interviewTemplate.getAuthor());
        holder.interviewDescription.setText(interviewTemplate.getDescription());
    }

    @Override
    public int getItemCount() {
        return interviewTemplates.size();
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.indexOfChild(v);
        if(position != -1)
            interviewPick.onInterviewPicked(interviewTemplates.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView interviewName;
        TextView interviewAuthor;
        TextView interviewDescription;


        ViewHolder(View itemView) {

            super(itemView);

            interviewName = (TextView) itemView.findViewById(R.id.interviewName);
            interviewAuthor = (TextView) itemView.findViewById(R.id.interviewAuthor);
            interviewDescription = (TextView) itemView.findViewById(R.id.interviewDescription);
        }
    }

}
