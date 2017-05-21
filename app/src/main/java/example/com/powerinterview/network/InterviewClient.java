package example.com.powerinterview.network;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.io.InputStream;

import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.utils.Encrypt;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.utils.Encrypt;


public class InterviewClient {


    public void storeInterviewModule(String token, Interview interview, AsyncHttpResponseHandler handler) throws EncryptionException, IOException {

        RequestParams params = new RequestParams();
        params.add("code", Encrypt.encryptByRSA(Encrypt.publicServerKey, token));
        params.add("name", interview.getName());
        params.add("description", interview.getDescription());
        params.add("password", !interview.getPassword().isEmpty() ? Encrypt.encryptByRSA(Encrypt.publicServerKey, interview.getPassword()) : "");
        params.add("hash", WebClient.getHash());
        params.put("file", interview.getInputStream());
        WebClient.post("store_pi_module.php",params, handler);

    }


    public void sendInterviewResults(String token, long interviewId, InputStream interviewLog, InputStream audio, String aesKey, AsyncHttpResponseHandler handler) throws EncryptionException, IOException {

        RequestParams params = new RequestParams();
        params.add("code", Encrypt.encryptByRSA(Encrypt.publicServerKey, token));
        params.add("interview_id", String.valueOf(interviewId));
        params.add("aes_key", Encrypt.encryptByRSA(Encrypt.publicServerKey, aesKey));
        params.add("hash", WebClient.getHash());
        if(audio != null)
            params.put("audio", audio);
        params.put("results", interviewLog);
        WebClient.post("upload_interview.php",params, handler);

    }

    public void getInterviewsModules(String token, JsonHttpResponseHandler handler) throws EncryptionException {

        RequestParams params = new RequestParams();
        params.add("code", Encrypt.encryptByRSA(Encrypt.publicServerKey, token));
        params.add("hash", WebClient.getHash());
        WebClient.post("get_interviews_modules.php", params, handler);
    }

    public void getInterviewModule(String token, String interviewId, AsyncHttpResponseHandler responseHandler) throws EncryptionException {

        RequestParams params = new RequestParams();
        params.add("code", Encrypt.encryptByRSA(Encrypt.publicServerKey, token));
        params.add("interview_id", interviewId);
        params.add("hash", WebClient.getHash());
        WebClient.post("get_interview_module.php", params, responseHandler);
    }

    public void activateCode(String token, String interviewCode, AsyncHttpResponseHandler responseHandler) throws EncryptionException {
        RequestParams params = new RequestParams();
        params.add("code", Encrypt.encryptByRSA(Encrypt.publicServerKey, token));
        params.add("interview_code", Encrypt.encryptByRSA(Encrypt.publicServerKey, interviewCode));
        params.add("hash", WebClient.getHash());
        WebClient.post("activate_interview_code.php", params, responseHandler);
    }

    public void getInterviewsReports(String token, JsonHttpResponseHandler handler) throws EncryptionException {

        RequestParams params = new RequestParams();
        params.add("code", Encrypt.encryptByRSA(Encrypt.publicServerKey, token));
        params.add("hash", WebClient.getHash());
        WebClient.post("get_reports.php", params, handler);
    }

    public void restoreReportKey(String token, long id, String aesKey, JsonHttpResponseHandler handler) throws EncryptionException {

        RequestParams params = new RequestParams();
        params.add("code", Encrypt.encryptByRSA(Encrypt.publicServerKey, token));
        params.add("key", Encrypt.encryptByRSA(Encrypt.publicServerKey, aesKey));
        params.add("id", String.valueOf(id));
        params.add("hash", WebClient.getHash());
        WebClient.post("restore_report_key.php", params, handler);
    }
}
