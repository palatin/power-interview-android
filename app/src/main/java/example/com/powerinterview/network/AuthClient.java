package example.com.powerinterview.network;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.model.Profile;
import example.com.powerinterview.model.User;
import example.com.powerinterview.utils.Encrypt;

/**
 * Created by Игорь on 15.04.2017.
 */

public class AuthClient {


    public void login(User user, String key, AsyncHttpResponseHandler handler) throws EncryptionException {

        RequestParams params = new RequestParams();
        params.add("email", user.getEmail());
        params.add("password", Encrypt.encryptByRSA(Encrypt.publicServerKey,user.getPassword()));
        params.add("key", Encrypt.encryptByRSA(Encrypt.publicServerKey, key));
        params.add("hash", WebClient.getHash());
        WebClient.post("login.php",params, handler);

    }

    public void register(Profile profile, JsonHttpResponseHandler handler) throws EncryptionException, FileNotFoundException {

        RequestParams params = new RequestParams();
        params.add("email", profile.getUser().getEmail());
        params.add("password", Encrypt.encryptByRSA(Encrypt.publicServerKey, profile.getUser().getPassword()));
        params.add("job", profile.getJob());
        params.add("name", profile.getName());
        params.add("org", profile.getOrganization());
        params.add("hash", WebClient.getHash());
        if(!profile.getImagePath().isEmpty()) {
            params.put("avatar", new File(profile.getImagePath()));
        }
        WebClient.post("register.php",params, handler);

    }

}
