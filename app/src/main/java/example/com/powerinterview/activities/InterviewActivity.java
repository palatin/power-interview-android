package example.com.powerinterview.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;
import example.com.powerinterview.R;
import example.com.powerinterview.core.BaseInterviewController;
import example.com.powerinterview.core.InterviewLogger;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.ConvertException;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.exceptions.InterviewElementNotFoundException;
import example.com.powerinterview.interfaces.InterviewProvider;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.managers.InterviewsTemplatesManager;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.Variable;
import example.com.powerinterview.network.InterviewClient;
import example.com.powerinterview.ui.CustomToast;
import example.com.powerinterview.utils.Converter;

public class InterviewActivity extends BaseWorkerActivity implements InterviewProvider {


    private AccountManager accountManager;
    private Unbinder unbinder;
    private InterviewsTemplatesManager interviewsTemplatesManager;
    private BaseInterviewController controller;
    private int interviewID;

    private InterviewClient interviewClient;

    private Interview interview;

    @BindView(R.id.interviewArea)
    LinearLayout interviewArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        File file = (File) getIntent().getSerializableExtra("template");
        interviewID = getIntent().getIntExtra("id", -1);
        if(interviewID == -1)
            finish();

        interviewsTemplatesManager = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewManager();
        accountManager = ((PowerInterviewApp) getApplication()).getAuthComponent().accountManager();
        interviewClient = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewClient();

        unbinder = ButterKnife.bind(this);

        try {

            interview = interviewsTemplatesManager.loadInterviewByFile(file);
            controller = new BaseInterviewController(this, ((PowerInterviewApp) getApplication()).getInterviewComponent().getWidgetsFactory());
            controller.initInterview(interview);

        } catch (IOException | ClassNotFoundException  e) {
            e.printStackTrace();
            showToast(e.getMessage(), CustomToast.ToastType.TOAST_ALERT);
        } catch (FactoryException e) {
            e.printStackTrace();
            showToast(e.getMessage(), CustomToast.ToastType.TOAST_ALERT);
        } catch (InterviewElementNotFoundException e) {
            e.printStackTrace();
            showToast(e.getMessage(), CustomToast.ToastType.TOAST_ALERT);
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void displayViews(View[] views) {
        interviewArea.removeAllViews();
        for (View view: views) {
            interviewArea.addView(view);
        }
    }

    @Override
    public void endInterview() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Interview is over")
                .setPositiveButton("Send results", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InterviewLogger.writeToInterviewLog("Interview is over");
                        InterviewLogger.writeToInterviewLog("Variables:");
                        Iterator it = interview.getVariables().entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, Variable> pair = (Map.Entry)it.next();
                            InterviewLogger.writeToInterviewLog(pair.getKey() + "---- " + pair.getValue().getValue());
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    private void sendInterview() {
        SecureRandom random = new SecureRandom();
        String aesKey = new BigInteger(130, random).toString(32);
        try {
            interviewClient.sendInterviewResults(accountManager.getToken(), interviewID, new ByteArrayInputStream(InterviewLogger.getResults(aesKey)),null, aesKey,
            new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    dismissProgressDialog();


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
