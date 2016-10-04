package uk.co.crystalcube.qualifications.rest;

import android.util.Log;

import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.crystalcube.qualifications.model.Qualification;

/**
 * Created by Tanveer Aslam on 24/09/16.
 */

public final class QualificationsRestClient {

    private static final String LOG_TAG = QualificationsRestClient.class.getSimpleName();
    private static final String ROOT_URL = "https://api.gojimo.net";

    private QualificationsRestApi restService;

    private QualificationsRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        restService = retrofit.create(QualificationsRestApi.class);
    }

    public static QualificationsRestClient build() {
        return new QualificationsRestClient();
    }

    /**
     * Returns 'ETag' header value.
     *
     * @return Etag hash string.
     * @see QualificationsRestApi#getQualificationHeaders()
     */
    public String getQualificationsETag() {

        try {

            Call<Response> call = restService.getQualificationHeaders();
            Headers headers = call.execute().headers();
            if (headers != null) {
                return headers.get("ETag");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Rest call failed: an exception occurred", e);
        }

        return null;
    }

    /**
     * @see QualificationsRestApi#getQualifications()
     */
    public List<Qualification> getQualifications() {

        try {
            Call<List<Qualification>> call = restService.getQualifications();
            Response<List<Qualification>> response = call.execute();
            if (response != null) {
                return response.body();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Rest call failed: an exception occurred", e);
        }

        return null;
    }

    /**
     * @see QualificationsRestApi#getQualification(String)
     */
    public Qualification getQualification(String id) {

        try {
            Call<Qualification> call = restService.getQualification(id);
            Response<Qualification> response = call.execute();
            if (response != null) {
                return response.body();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Rest call failed: an exception occurred", e);
        }

        return null;
    }
}
