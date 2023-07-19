package entity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "entity.model.OspRightsMatcherTask")
@Table(name = "OSP_RIG_MATCHER_TASK_DBF")
public class OspRightsMatcherTask {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    public static final boolean ACCESS = true;
    public static final boolean NO_ACCESS = false;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "M_REFERENCE")
    private long reference;

    @Column(name = "M_TASK_LABEL")
    private String taskLabel;

    @Column(name = "M_RIGHT", columnDefinition = "numeric")
    private boolean accessRight;

    @Column(name = "M_OSP_RIG_MATCHER")
    private long ospRightsMatcherReference;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public OspRightsMatcherTask(long reference, String taskLabel, boolean accessRight, long ospRightsMatcherReference) {
        this.reference = reference;
        this.taskLabel = taskLabel;
        this.accessRight = accessRight;
        this.ospRightsMatcherReference = ospRightsMatcherReference;
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public long getReference() {
        return reference;
    }

    public void setReference(long reference) {
        this.reference = reference;
    }

    public String getTaskLabel() {
        return taskLabel;
    }

    public void setTaskLabel(String taskLabel) {
        this.taskLabel = taskLabel;
    }

    public boolean isAccessRight() {
        return accessRight;
    }

    public void setAccessRight(boolean accessRight) {
        this.accessRight = accessRight;
    }

    public long getOspRightsMatcherReference() {
        return ospRightsMatcherReference;
    }

    public void setOspRightsMatcherReference(long ospRightsMatcherReference) {
        this.ospRightsMatcherReference = ospRightsMatcherReference;
    }

    public Long getId() {
        return reference;
    }

}
