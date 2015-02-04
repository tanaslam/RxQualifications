package uk.co.crystalcube.aatemplate.rest;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.net.InetSocketAddress;
import java.net.Proxy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import uk.co.crystalcube.aatemplate.model.DummyObject;

/**
 * Created by tanny on 04/02/15.
 */

@EBean (scope = EBean.Scope.Singleton)
public class RestClient {

    private static final int HTTP_REQUEST_CONNECTION_TIMEOUT = 5000;
    private static final int HTTP_REQUEST_READ_TIMEOUT = 15000;
    private static final boolean SHOULD_USE_PROXY = false;

    @RestService
    protected TemplateRestApi restService;

    @AfterInject
    void setupRestClient() {
        restService.setRootUrl("https://api.example.com");
        setRequestFactory();
    }

    private void setRequestFactory() {

        HttpsClientRequestFactory factory =
                new HttpsClientRequestFactory(getHostnameVerifier());

        factory.setConnectTimeout(HTTP_REQUEST_CONNECTION_TIMEOUT);
        factory.setReadTimeout(HTTP_REQUEST_READ_TIMEOUT);

        if(SHOULD_USE_PROXY) {

            InetSocketAddress proxyHost =
                    new InetSocketAddress("192.0.0.10", 8080);

            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyHost);
            factory.setProxy(proxy);
        }

        restService.getRestTemplate().setRequestFactory(factory);
    }

    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {

            /**
             * Implements {@link javax.net.ssl.HostnameVerifier}
             * and verifies hostname of RESTful backend API server by string matching with build configuration.
             *
             * @return true if RESTful API root URI contains host name.
             */
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return "http://api.example.com".contains(hostname);
            }
        };
    }

    @Background
    public DummyObject getRestObject(String id) {

        DummyObject object = null;

        try {
            object = (DummyObject) restService.getObject(id);
        } catch (RestClientException e) {
            // log error here
        }

        return object;
    }
}
