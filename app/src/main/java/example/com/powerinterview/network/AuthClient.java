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

    private String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAswtsGHh1wvtUeYX8BIHy" +
            "BOGtqLYUOOw6YI/8aWGwU6fLXBj6+fxZRQX/j3G9XdrgvnFe/DzXSglQ+RofX8t8" +
            "cxwKkGATnfHC91x69V8KCWMJS2r9zish8FdKwmY/H+p6YTxvaoQoCgLj7S3XCvC0" +
            "HD6fYrnzuHSqofIOoyiDgkW3OP57mQfCZu+rtZdrD1IisVPs9DYZ4GGBElIwhjed" +
            "CvO9qwJxmCypMs5Xmx0nG+CxNgkHQPj8cOE9UG3qwnI/ubvqVOpvs6HHHe8unF7z" +
            "gBDWQ/fb+ZLSxBwbT+F0LJK2rVTWBEIHUjKzCU6wFu1raaDKs8fqsD0Qr0wzwHEL" +
            "GwIDAQAB";


    public void login(User user, AsyncHttpResponseHandler handler) throws EncryptionException {

        RequestParams params = new RequestParams();
        params.add("email", user.getEmail());
        params.add("password", Encrypt.encryptByRSA("",user.getPassword()));
        params.add("hash", WebClient.getHash());
        WebClient.post("login.php",params, handler);

    }

}
