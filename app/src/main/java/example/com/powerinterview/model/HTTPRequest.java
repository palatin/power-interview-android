package example.com.powerinterview.model;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by palatin on 3/22/17.
 */

public class HTTPRequest {

    public HTTPMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPMethod method) {
        this.method = method;
    }

    public enum HTTPMethod {POST, GET}

    private HTTPMethod method;

    private String url;

    private RequestParams params;

    private AsyncHttpResponseHandler handler;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestParams getParams() {
        return params;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    public AsyncHttpResponseHandler getHandler() {
        return handler;
    }

    public void setHandler(AsyncHttpResponseHandler handler) {
        this.handler = handler;
    }



}
