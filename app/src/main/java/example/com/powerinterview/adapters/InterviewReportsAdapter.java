package example.com.powerinterview.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.com.powerinterview.R;
import example.com.powerinterview.interfaces.InterviewPick;
import example.com.powerinterview.model.InterviewReport;
import example.com.powerinterview.model.InterviewTemplate;

/**
 * Created by Игорь on 20.05.2017.
 */

public class InterviewReportsAdapter  extends RecyclerView.Adapter<InterviewReportsAdapter.ViewHolder> implements View.OnClickListener {

    private List<InterviewReport> interviewReports;

    private RecyclerView recyclerView;

    private ReportPicker reportPicker;

    public InterviewReportsAdapter(List<InterviewReport> interviewReports, ReportPicker reportPicker, RecyclerView recyclerView) {
        this.interviewReports = interviewReports;
        this.reportPicker = reportPicker;
        this.recyclerView = recyclerView;
    }

    @Override
    public InterviewReportsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View interviewReportView = inflater.inflate(R.layout.report_list_item, parent, false);
        InterviewReportsAdapter.ViewHolder holder = new InterviewReportsAdapter.ViewHolder(interviewReportView);

        interviewReportView.setOnClickListener(this);


        return holder;
    }

    @Override
    public void onBindViewHolder(InterviewReportsAdapter.ViewHolder holder, int position) {
        InterviewReport interviewReport = interviewReports.get(position);
        holder.interviewName.setText(interviewReport.getTemplateName());
        holder.respondentEmail.setText(recyclerView.getContext().getString(R.string.respondent_email_string) + " " + interviewReport.getRespondentEmail());
        holder.date.setText(interviewReport.getDate());
    }

    @Override
    public int getItemCount() {
        return interviewReports.size();
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.indexOfChild(v);
        if(position != -1)
            reportPicker.onPick(interviewReports.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView interviewName;
        TextView respondentEmail;
        TextView date;


        ViewHolder(View itemView) {

            super(itemView);

            interviewName = (TextView) itemView.findViewById(R.id.interviewName);
            respondentEmail = (TextView) itemView.findViewById(R.id.respondentEmail);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    public interface ReportPicker {

        void onPick(InterviewReport report);

    }


}
