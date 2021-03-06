package example.com.powerinterview.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;
import example.com.powerinterview.R;
import example.com.powerinterview.core.BaseInterviewController;
import example.com.powerinterview.core.BaseQuestionController;
import example.com.powerinterview.core.InterviewLogger;
import example.com.powerinterview.core.PowerInterviewApp;
import example.com.powerinterview.exceptions.ConvertException;
import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.exceptions.InterviewElementNotFoundException;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.InterviewProvider;
import example.com.powerinterview.managers.AccountManager;
import example.com.powerinterview.managers.InterviewsTemplatesManager;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.model.Variable;
import example.com.powerinterview.network.InterviewClient;
import example.com.powerinterview.ui.CustomToast;
import example.com.powerinterview.utils.Converter;
import example.com.powerinterview.utils.Encrypt;
import example.com.powerinterview.utils.Validator;

public class InterviewActivity extends BaseWorkerActivity implements InterviewProvider {


    private AccountManager accountManager;
    private Unbinder unbinder;
    private InterviewsTemplatesManager interviewsTemplatesManager;
    private BaseInterviewController controller;
    private long interviewID;

    private InterviewClient interviewClient;

    private Interview interview;

    @BindView(R.id.interviewArea)
    LinearLayout interviewArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);

        File file = (File) getIntent().getSerializableExtra("template");
        interviewID = getIntent().getLongExtra("id", -1);
        if(interviewID == -1)
            finish();

        interviewsTemplatesManager = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewManager();
        accountManager = ((PowerInterviewApp) getApplication()).getAuthComponent().accountManager();
        interviewClient = ((PowerInterviewApp) getApplication()).getInterviewComponent().getInterviewClient();

        unbinder = ButterKnife.bind(this);

        try {

            interview = interviewsTemplatesManager.loadInterviewByFile(file);
            IPIWidgetsFactory factory = ((PowerInterviewApp) getApplication()).getInterviewComponent().getWidgetsFactory();
            controller = new BaseInterviewController(this, new BaseQuestionController(factory));
            controller.initInterview(interview);

        } catch (Exception  e) {
            e.printStackTrace();
            handleException(e);
        }

        //clear log
        InterviewLogger.clearLog();


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

        final EditText respondentEmail = new EditText(this);
        respondentEmail.setHint(getString(R.string.respondent_email_string));
        respondentEmail.setMaxEms(20);
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
                        String email = respondentEmail.getText().toString();
                        if(email.isEmpty() || !Validator.checkEmail(email)) {
                            InterviewActivity.this.showToast("Incorrect respondent's email", CustomToast.ToastType.TOAST_ALERT);
                            dialog.dismiss();
                            return;
                        }
                        sendInterview(email);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.setView(respondentEmail);
        alertDialog.show();
    }

    @Override
    public void handleException(Exception ex) {
        super.handleException(ex);
    }

    private void sendInterview(String respondentEmail) {

        String aes = null;
        try {
            aes = Encrypt.generateRandomAESKey();
        } catch (EncryptionException e) {
            e.printStackTrace();
            return;
        }

        try {
            showProgressDialog(getString(R.string.uploading));
            interviewClient.sendInterviewResults(accountManager.getToken(), interviewID, respondentEmail, new ByteArrayInputStream(InterviewLogger.getResults(aes)),null, aes,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        dismissProgressDialog();

                        try {
                            if(displayResult(response)) {

                                showSuccessDialog(response.getString("key"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        dismissProgressDialog();
                        writeDebugLog("Interview loader", "exception from server, status code: " + statusCode + "message: " + responseString);
                    }
                });
        } catch (Exception e) {
            dismissProgressDialog();
            e.printStackTrace();
        }
    }

    private void showSuccessDialog(String key) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.inrerview_upload_success)  + key)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
