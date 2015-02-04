package uk.co.crystalcube.aatemplate.rest;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tanny on 04/02/15.
 */
@Accept(MediaType.APPLICATION_JSON)
@Rest(
        converters = {CustomGsonConverter.class,
                StringHttpMessageConverter.class,
                FormHttpMessageConverter.class},

        interceptors = {CustomRequestInterceptor.class})
public interface TemplateRestApi extends RestClientErrorHandling {

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

    /**
     * Get a dummy object from back-end, replace template params accordingly.
     *
     * @param id unique identifier of the object
     * @return Model object parsed from JSON
     */
    @Get("/object/{id}")
    Object getObject(String id);
}

