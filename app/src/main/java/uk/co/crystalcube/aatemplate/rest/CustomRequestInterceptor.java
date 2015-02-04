package uk.co.crystalcube.aatemplate.rest;

import android.util.Log;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by tanny on 04/02/15.
 */
public class CustomRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final String LOG_TAG = CustomRequestInterceptor.class.getSimpleName();

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        Log.v(LOG_TAG, "Issuing request( method:" + request.getMethod() +
                " to: " + request.getURI());
        Log.v(LOG_TAG, "Uploading data:  " + new String(body));

        return execution.execute(request, body);
    }
}
