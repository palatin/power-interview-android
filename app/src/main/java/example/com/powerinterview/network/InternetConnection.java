package example.com.powerinterview.network;

import android.webkit.WebViewClient;

import example.com.powerinterview.interfaces.IInternetConnectionActivity;

/**
 * Created by palatin on 3/22/17.
 */

public class InternetConnection {

    private static IInternetConnectionActivity activity;

    public static void bind(IInternetConnectionActivity activity) {

        InternetConnection.activity = activity;
    }

    public static void unbind() {

        InternetConnection.activity = null;

        WebClient.unbindRequest();
    }


    public static IInternetConnectionActivity getActivity() {
        return activity;
    }
}
