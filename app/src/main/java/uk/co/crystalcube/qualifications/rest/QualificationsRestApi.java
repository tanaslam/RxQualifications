package uk.co.crystalcube.qualifications.rest;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Head;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import uk.co.crystalcube.qualifications.model.Qualification;
import uk.co.crystalcube.qualifications.model.Qualifications;
import uk.co.crystalcube.qualifications.model.Subject;

/**
 * Created by tanny on 04/02/15.
 */
@Accept(MediaType.APPLICATION_JSON)
@Rest(
        converters = {CustomGsonConverter.class,
                StringHttpMessageConverter.class,
                FormHttpMessageConverter.class},

        interceptors = {CustomRequestInterceptor.class})
public interface QualificationsRestApi extends RestClientErrorHandling {

    /**
     * Root URL of back-end REST API.
     *
     * @param rootUrl root URL of RESTful API from configuration.
     */
    void setRootUrl(String rootUrl);
    RestTemplate getRestTemplate();

    /**
     * Set additional headers for request.
     *
     * @param name Header name
     * @param value Header value
     */
    void setHeader(String name, String value);

    /**
     * Set custom REST template.
     * This allows to override REST template configuration that is set by
     * default.
     *
     * @param restTemplate an instance of custom {@link RestTemplate}
     */
    void setRestTemplate(RestTemplate restTemplate);

    @Head("/api/v4/qualifications")
    HttpHeaders getQualificationHeaders();

    /**
     * Get a list of qualifications from back-end RESTful API.
     *
     * @return Model object that wraps up list of of qualifications
     */
    @Get("/api/v4/qualifications")
    List<Qualification> getQualifications();


    @Get("/api/v4/qualifications/{qualificationId}")
    Qualification getQualification(String qualificationId);

}

