package uk.co.crystalcube.qualifications.rest;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import uk.co.crystalcube.qualifications.model.Qualification;
import uk.co.crystalcube.qualifications.model.Qualifications;

/**
 * Created by tanny on 04/02/15.
 */

@EBean (scope = EBean.Scope.Singleton)
public class QualificationsRestClient implements RestErrorHandler {

    private static final String LOG_TAG = QualificationsRestClient.class.getSimpleName();
    private static final String ROOT_URL = "https://api.gojimo.net";

    @RestService
    protected QualificationsRestApi restService;

    @Bean
    protected Qualifications qualifications;

    @AfterInject
    void setupRestClient() {
        restService.setRootUrl(ROOT_URL);
        restService.setRestErrorHandler(this);
    }

    public void getQualifications() {

        List<Qualification> response = null;

        try {

            HttpHeaders headers = restService.getQualificationHeaders();
            if(qualifications.getETag().equals(headers.getETag())) {
                return;
            }

            response = restService.getQualifications();

            qualifications.setQualificationList(response);
            qualifications.setETag(headers.getETag());

        } catch (RestClientException e) {
            Log.e(LOG_TAG, "Rest call failed: an exception occurred", e);
        }
    }

    public Qualification getQualification(String id) {

        Qualification object = null;

        try {
            object = restService.getQualification(id);
        } catch (RestClientException e) {
            Log.e(LOG_TAG, "Rest call failed: an exception occurred", e);
        }

        return object;
    }

    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        Log.e(LOG_TAG, "Rest call failed: an exception occurred", e);
    }
}
