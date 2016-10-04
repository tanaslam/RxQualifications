package uk.co.crystalcube.qualifications.rest;


import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Path;
import uk.co.crystalcube.qualifications.model.Qualification;
import uk.co.crystalcube.qualifications.model.Subject;

/**
 * The rest client interface that exposes required API endpoints.
 * <p>
 * Created by Tanveer Aslam on 02/10/16.
 */
public interface QualificationsRestApi {


    /**
     * Requests response with empty body. This is mainly to check entity-tag header.
     *
     * @return {@link Response}
     */
    @HEAD("/api/v4/qualifications")
    Call<Response> getQualificationHeaders();

    /**
     * Get a list of qualifications from back-end RESTful API.
     *
     * @return Model object that wraps up list of of qualifications
     */
    @GET("/api/v4/qualifications")
    Call<List<Qualification>> getQualifications();


    /**
     * Fetch {@link Qualification} object matching qualification id.
     *
     * @param qualificationId String containing qualification id.
     * @return The retrofit call object of response type: {@link Qualification}
     */
    @GET("/api/v4/qualifications/{qualificationId}")
    Call<Qualification> getQualification(@Path("qualificationId") String qualificationId);

    /**
     * Fetch {@link Subject} object matching qualification id.
     *
     * @param  subjectId String containing subject id.
     * @return The retrofit call object of response type: {@link Subject}
     */
    @GET("/api/v4/qualifications/{subjectId}")
    Call<Subject> getSubject(@Path("subjectId") String subjectId);

}

