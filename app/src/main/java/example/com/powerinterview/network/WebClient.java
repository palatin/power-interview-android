package example.com.powerinterview.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;

import example.com.powerinterview.exceptions.EncryptionException;
import example.com.powerinterview.interfaces.IInternetConnectionActivity;
import example.com.powerinterview.model.HTTPRequest;
import example.com.powerinterview.utils.Encrypt;

/**
 * Created by Игорь on 30.03.2017.
 */

public class WebClient {


    private static final String BASE_URL = "http://159.203.25.140/childfirst_api/";

    private static HTTPRequest currentRequest;

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String scriptURL, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if(isOnline())
            client.get(getAbsoluteUrl(scriptURL), params, responseHandler);
        else {
            currentRequest = new HTTPRequest();
            currentRequest.setUrl(scriptURL);
            currentRequest.setParams(params);
            currentRequest.setHandler(responseHandler);
            currentRequest.setMethod(HTTPRequest.HTTPMethod.GET);
        }

    }

    public static void post(String scriptURL, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if(isOnline())
            client.post(getAbsoluteUrl(scriptURL), params, responseHandler);
        else {
            currentRequest = new HTTPRequest();
            currentRequest.setUrl(scriptURL);
            currentRequest.setParams(params);
            currentRequest.setHandler(responseHandler);
            currentRequest.setMethod(HTTPRequest.HTTPMethod.POST);
        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static String getHash() throws EncryptionException {
        SimpleDateFormat df = new SimpleDateFormat("ddMMHH");
        df.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = df.format(new Date());
        return Encrypt.encryptMD5(gmtTime + "SHIA");
    }


    private static boolean isOnline() {
        IInternetConnectionActivity activity = InternetConnection.getActivity();
        if(activity != null) {
            ConnectivityManager cm = (ConnectivityManager) activity.getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo == null || !netInfo.isConnected()) {
                activity.notifyAboutConnectionLack();
                showSnackBar(activity.getSnackBarLayout());
                return false;
            }
            return true;
        }
        return true;
    }

    private static void showSnackBar(View view) {
        Snackbar.make(view, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(currentRequest.getMethod().equals(HTTPRequest.HTTPMethod.GET))
                            get(currentRequest.getUrl(), currentRequest.getParams(), currentRequest.getHandler());
                        else
                            post(currentRequest.getUrl(), currentRequest.getParams(), currentRequest.getHandler());
                    }
                }).show();
    }

    public static void unbindRequest() {
        currentRequest = null;
    }

}
