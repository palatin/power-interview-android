package example.com.powerinterview.network;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.model.User;
import example.com.powerinterview.utils.Encrypt;

/**
 * Created by Игорь on 15.04.2017.
 */

public class AuthClient {
    public void login(User user, AsyncHttpResponseHandler handler) throws EncryptionException {

        RequestParams params = new RequestParams();
        params.add("email", user.getEmail());
        params.add("password", Encrypt.encryptByRSA(Encrypt.publicServerKey,user.getPassword()));
        params.add("hash", WebClient.getHash());
        WebClient.post("login.php",params, handler);

    }

}
