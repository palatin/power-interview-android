package example.com.powerinterview.network;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.model.Interview;
import example.com.powerinterview.utils.Encrypt;

/**
 * Created by Игорь on 20.04.2017.
 */

public class InterviewClient {


    public void storeInterviewModule(String token, Interview interview, AsyncHttpResponseHandler handler) throws EncryptionException, IOException {

        RequestParams params = new RequestParams();
        params.add("code", token);
        params.add("name", interview.getName());
        params.add("description", interview.getDescription());
        params.add("password", Encrypt.encryptByRSA(Encrypt.publicServerKey,interview.getPassword()));
        params.add("hash", WebClient.getHash());
        params.put("file", interview.getInputStream());
        WebClient.post("store_pi_module.php",params, handler);

    }


}
