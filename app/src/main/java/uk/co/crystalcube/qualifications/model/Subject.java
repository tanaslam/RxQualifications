
package uk.co.crystalcube.qualifications.model;

import com.google.gson.annotations.Expose;

public class Subject {

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String link;
    @Expose
    private String colour;

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
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
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

    /**
     * 
     * @return
     *     The colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * 
     * @param colour
     *     The colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

}
