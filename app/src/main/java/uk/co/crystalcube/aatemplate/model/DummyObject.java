package uk.co.crystalcube.aatemplate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.androidannotations.annotations.EBean;

/**
 * Created by tanny on 04/02/15.
 */
@EBean
public class DummyObject {

    @Expose @SerializedName("id")
    private String id;

    @Expose @SerializedName("name")
    private String name;

}
