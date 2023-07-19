package entity.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "entity.model.OspRightsMatcher")
@Table(name = "OSP_RIG_MATCHER_DBF")
public class OspRightsMatcher {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "M_REFERENCE")
    private long reference;

    @Column(name = "M_LABEL")
    private String label;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "M_OSP_RIG_MATCHER")
    private Set<OspRightsMatcherTask> ospRightsMatcherTask;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Constructors 
    //~ ----------------------------------------------------------------------------------------------------------------

    public OspRightsMatcher(long reference, String label, Set<OspRightsMatcherTask> ospRightsMatcherTask) {
        this.reference = reference;
        this.label = label;
        this.ospRightsMatcherTask = ospRightsMatcherTask;
    }

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    public Long getId() {
        return reference;
    }

    public long getReference() {
        return reference;
    }

    public void setReference(long reference) {
        this.reference = reference;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<OspRightsMatcherTask> getOspRightsMatcherTask() {
        return ospRightsMatcherTask;
    }

    public void setOspRightsMatcherTask(Set<OspRightsMatcherTask> ospRightsMatcherTask) {
        this.ospRightsMatcherTask = ospRightsMatcherTask;
    }
}
