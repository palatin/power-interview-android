package example.com.powerinterview.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;
import example.com.powerinterview.R;
import example.com.powerinterview.adapters.InterviewsAdapter;
import example.com.powerinterview.core.AppConfig;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.ConvertException;
import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.interfaces.InterviewPick;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.managers.InterviewManager;
import example.com.powerinterview.model.InterviewModule;
import example.com.powerinterview.network.InterviewClient;
import example.com.powerinterview.utils.Converter;

public class InterviewPickerActivity extends BaseWorkerActivity implements InterviewPick {


    private InterviewClient client;
    private AccountManager accountManager;
    private List<InterviewModule> interviewModules;
    private InterviewsAdapter adapter;
    private Unbinder unbinder;
    private InterviewManager interviewManager;

    @BindView(R.id.interviewsRecyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_picker);
        client = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewClient();
        interviewManager = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewManager();
        accountManager = ((PowerInterviewApp) getApplication()).getAuthComponent().accountManager();

        unbinder = ButterKnife.bind(this);
        loadInterviews();
    }

    private void loadInterviews() {

        interviewModules = new ArrayList<>();
        adapter = new InterviewsAdapter(interviewModules, this, recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            client.getInterviewsModules(accountManager.getToken(), new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    writeDebugLog("Interview loader", "list of interviews loaded " + response.toString());


                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject moduleJSON = response.getJSONObject(i);
                            InterviewModule module = new InterviewModule();
                            module.setId(moduleJSON.getLong("id"));
                            module.setName(moduleJSON.getString("name"));
                            module.setAuthor(moduleJSON.getString("author"));
                            module.setDescription(moduleJSON.getString("description"));
                            interviewModules.add(module);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    if(interviewModules.size() > 0)
                        adapter.notifyItemRangeInserted(0, interviewModules.size());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onInterviewPicked(final InterviewModule interviewModule) {
        try {
            client.getInterviewModule(accountManager.getToken(), String.valueOf(interviewModule.getId()), new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    for (Header header: headers) {
                        writeDebugLog("Interview loader", "Result form loading interview module: " + header.getName() + " " + header.getValue());
                        if(header.getName().equals("Content-Type")) {
                            if(header.getValue().equals("application/json")) {
                                try {
                                    displayResult(Converter.bytesToJSON(responseBody));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (ConvertException e) {
                                    e.printStackTrace();
                                }
                            }
                            else if(header.getValue().equals("application/octet-stream")) {
                                try {
                                    interviewManager.storeInterviewModule(interviewModule, getApplicationContext(), responseBody);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    writeDebugLog("Interview loader", "exception from server, status code: " + statusCode);

                }
            });
        } catch (EncryptionException e) {
            e.printStackTrace();
        }
    }
}
