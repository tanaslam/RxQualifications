
package uk.co.crystalcube.qualifications.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Qualification {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private List<Subject> subjects = new ArrayList<Subject>();
    @SerializedName("default_products")
    @Expose
    private List<Object> defaultProducts = new ArrayList<Object>();
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @Expose
    private String link;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The subjects
     */
    public List<Subject> getSubjects() {
        return subjects;
    }

    /**
     * 
     * @param subjects
     *     The subjects
     */
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    /**
     * 
     * @return
     *     The defaultProducts
     */
    public List<Object> getDefaultProducts() {
        return defaultProducts;
    }

    /**
     * 
     * @param defaultProducts
     *     The default_products
     */
    public void setDefaultProducts(List<Object> defaultProducts) {
        this.defaultProducts = defaultProducts;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

}
