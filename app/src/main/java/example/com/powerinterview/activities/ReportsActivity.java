package example.com.powerinterview.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.Base64;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.InterviewReportsAdapter;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.interfaces.InterviewPick;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.model.InterviewReport;
import example.com.powerinterview.model.InterviewTemplate;
import example.com.powerinterview.network.InterviewClient;
import example.com.powerinterview.utils.Encrypt;
import example.com.powerinterview.widgets.PIBootstrapButton;

public class ReportsActivity extends BaseWorkerActivity implements InterviewReportsAdapter.ReportPicker {

    private Unbinder unbinder;
    private InterviewClient client;
    private AccountManager accountManager;

    private InterviewReportsAdapter adapter;

    private List<InterviewReport> reports = new ArrayList<>();

    private AlertDialog dialog;

    @BindView(R.id.reportsRecyclerView)
    public RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        unbinder = ButterKnife.bind(this);

        adapter = new InterviewReportsAdapter(reports, this, recyclerView);
        client = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewClient();
        accountManager = ((PowerInterviewApp) getApplication()).getAuthComponent().accountManager();



        loadReports();
    }


    /**
     * Method get list of user reports from server
     * then populate reports list and notify adapter
     */
    private void loadReports() {

        try {
            client.getInterviewsReports(accountManager.getToken(), new JsonHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        displayResult(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


                    writeDebugLog("Interview report", "list of reports loaded " + response.toString());

                    int currentSize = reports.size();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject moduleJSON = response.getJSONObject(i);
                            InterviewReport report = new InterviewReport();
                            report.setId(moduleJSON.getLong("id"));
                            report.setRespondentEmail(moduleJSON.getString("respondent_email"));
                            report.setTemplateName(moduleJSON.getString("template_name"));
                            report.setDate(moduleJSON.getString("date"));
                            reports.add(report);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ReportsActivity.this));
                    if(reports.size() > 0)
                        adapter.notifyItemRangeInserted(currentSize, reports.size());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    writeDebugLog("Interview loader", "exception from server, status code: " + statusCode + "message: " + responseString);

                }
            });
        } catch (EncryptionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method call dialog to restore secret key
     * @param report - user picked report object
     */
    @Override
    public void onPick(final InterviewReport report) {

        dialog = new AlertDialog.Builder(this)
                .setMessage(R.string.restore_report_key)
                .setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.done), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restoreKey(report);
                    }
                })
                .create();
        dialog.show();
    }

    /**
     * Method send request to restore key
     * @param report - user picked report object
     */
    private void restoreKey(InterviewReport report) {

        try {

            showProgressDialog(getString(R.string.please_wait));
            final String aesKey = Encrypt.generateRandomAESKey();

            client.restoreReportKey(accountManager.getToken(), report.getId(), aesKey, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    dismissProgressDialog();

                    writeDebugLog("Interview restore report key", "answer from server" + response.toString());

                    try {
                        if(displayResult(response)) {
                            showNewCode(Encrypt.decryptAES(response.getString("key"), aesKey));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dismissProgressDialog();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    dismissProgressDialog();
                    writeDebugLog("Interview key restore", "exception from server, status code: " + statusCode + "message: " + responseString);

                }
            });
        } catch (EncryptionException e) {
            dismissProgressDialog();
            e.printStackTrace();
        }
    }

    /**
     * Show the new code to the user
     * @param code - new code from server
     */
    private void showNewCode(String code) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this)
                .setMessage(getString(R.string.new_report_key)  + code)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
