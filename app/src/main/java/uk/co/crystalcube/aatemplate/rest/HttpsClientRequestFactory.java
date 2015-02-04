package uk.co.crystalcube.aatemplate.rest;

import android.util.Log;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by tanny on 04/02/15.
 */

public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {

    private static final String LOG_TAG = HttpsClientRequestFactory.class.getSimpleName();

    private final HostnameVerifier hostNameVerifier;

    public HttpsClientRequestFactory(HostnameVerifier hostNameVerifier) {
        this.hostNameVerifier = hostNameVerifier;
    }

    @Override
    protected void prepareConnection(final HttpURLConnection connection, final String httpMethod)
            throws IOException {
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setHostnameVerifier(hostNameVerifier);
            ((HttpsURLConnection) connection).setSSLSocketFactory(initSSLContext().getSocketFactory());
        }
        super.prepareConnection(connection, httpMethod);
    }

    private SSLContext initSSLContext() {

        SSLContext context = null;

        try {

            context = SSLContext.getInstance("TLSv1");

            TrustManager[] tm = new TrustManager[]{new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }};

            //init context with trust manager that trusts all hosts
            context.init(null, tm, new SecureRandom());

        } catch (NoSuchAlgorithmException nae) {
            Log.e(LOG_TAG, "No such algorithm: TLSv1, " + nae.getLocalizedMessage());
        } catch (KeyManagementException kme) {
            Log.e(LOG_TAG, "SSL context initialisation failed: " + kme.getLocalizedMessage());
        }

        return context;
    }

}
