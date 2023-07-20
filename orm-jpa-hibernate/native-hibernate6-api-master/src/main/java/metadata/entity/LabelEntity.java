package metadata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "metadata.entity.LabelEntity")
@Table(name = "t_label_entity")
public class LabelEntity {

    @Id
    @Column(name = "M_REFERENCE")
    private Long reference;

    @Column(name = "M_LABEL")
    private String label;

    @Column(name = "M_IS_VALID")
    private int isValid;

    public LabelEntity() {
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
}
