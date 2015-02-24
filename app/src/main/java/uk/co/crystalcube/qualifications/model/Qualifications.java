package uk.co.crystalcube.qualifications.model;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanny on 04/02/15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Qualifications {

    /** ETag header value */
    private String eTag = "";

    private List<Qualification> qualificationList = new ArrayList<>();

    public String getETag() {
        return eTag;
    }

    public void setETag(String eTag) {
        this.eTag = eTag == null ? "" : eTag;
    }

    public List<Qualification> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<Qualification> qualificationList) {
        if(qualificationList == null)
            return;

        this.qualificationList = qualificationList;
    }

    public Qualification findQualificationById(String id) {

        for(Qualification q : qualificationList) {
            if(q.getId().equals(id)) {
                return q;
            }
        }

        return null;
    }

    public List<Subject> getSubjectForQualification(String qualificationId) {

        Qualification q = findQualificationById(qualificationId);

        if(q != null) {
            return q.getSubjects();
        }

        return null;
    }
}
