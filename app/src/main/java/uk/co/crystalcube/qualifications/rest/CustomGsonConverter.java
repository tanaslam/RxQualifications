package uk.co.crystalcube.qualifications.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * Created by tanny on 04/02/15.
 */
public class CustomGsonConverter extends AbstractHttpMessageConverter<Object>
        implements HttpMessageConverter<Object> {

    private static final String LOG_TAG = CustomGsonConverter.class.getSimpleName();
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Gson gson;

    public CustomGsonConverter() {
        this(true);
    }

    public CustomGsonConverter(boolean serialiseNulls) {
        this(serialiseNulls ? new GsonBuilder().serializeNulls()
                .excludeFieldsWithoutExposeAnnotation().create()
                : new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create());
    }

    public CustomGsonConverter(Gson gson) {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
        setGson(gson);
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage httpInputMessage)
            throws IOException, HttpMessageNotReadableException {

        Reader json = new InputStreamReader(httpInputMessage.getBody(),
                getCharset(httpInputMessage.getHeaders()));

        try {
            return gson.fromJson(json, clazz);
        } catch (JsonParseException pe) {
            throw new HttpMessageNotReadableException("Couldn't de-serialised http response", pe);
        } finally {
            json.close();
        }
    }

    @Override
    protected void writeInternal(Object modelObject, HttpOutputMessage httpOutputMessage)
            throws IOException, HttpMessageNotWritableException {

        OutputStreamWriter writer = new OutputStreamWriter(httpOutputMessage.getBody(),
                getCharset(httpOutputMessage.getHeaders()));
        try {
            gson.toJson(modelObject, writer);
        } catch (JsonIOException pe) {
            throw new HttpMessageNotWritableException("Couldn't serialised object", pe);
        }
    }

    @Override
    public boolean canRead(Class<?> contextClass, MediaType mediaType) {
        return canRead(mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return canWrite(mediaType);
    }

    private void setGson(Gson gson) {
        this.gson = gson;
    }

    private Charset getCharset(HttpHeaders headers) {
        if (headers != null && headers.getContentType() != null
                && headers.getContentType().getCharSet() != null) {
            return headers.getContentType().getCharSet();
        }
        return DEFAULT_CHARSET;
    }
}
