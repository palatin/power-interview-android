package example.com.powerinterview.network;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

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

}
