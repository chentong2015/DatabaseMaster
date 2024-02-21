package hibernate.framework.apis.mappings.element.collection;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "MASTER_AGR_DBF")
@Access(AccessType.FIELD)
public class MasterAgreementEntity {

    @Id
    @Column(name = "M_ID", nullable = false, columnDefinition = "numeric(10)")
    @Access(AccessType.PROPERTY)
    private Long reference;

    @Column(name = "M_VERSION", nullable = false, columnDefinition = "numeric(6)")
    private int version;

    @Column(name = "M_LABEL", columnDefinition = "varchar(255)")
    private String label;

    @ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "MASTER_AGR_TYPOLOGY_DBF", joinColumns = @JoinColumn(name = "M_MASTER_AGR_ID", referencedColumnName = "M_ID"))
    @Column(name = "M_TYPOLOGY")
    private Set<Long> typologyReferences;

    @ElementCollection(targetClass = MasterAgreementPartyEntity.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "MASTER_AGR_PARTY_DBF", joinColumns = @JoinColumn(name = "M_MASTER_AGR_ID", referencedColumnName = "M_ID"))
    @JoinColumn(name = "M_MASTER_AGR_ID")
    private Set<MasterAgreementPartyEntity> partyEntities;

    public MasterAgreementEntity() {
    }

    public MasterAgreementEntity(Long reference, int version, String label, Set<Long> typologyReferences, Set<MasterAgreementPartyEntity> partyEntities) {
        this.reference = reference;
        this.version = version;
        this.label = label;
        this.typologyReferences = typologyReferences;
        this.partyEntities = partyEntities;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Long> getTypologyReferences() {
        return typologyReferences;
    }

    public void setTypologyReferences(Set<Long> typologyReferences) {
        this.typologyReferences = typologyReferences;
    }

    public Set<MasterAgreementPartyEntity> getPartyEntities() {
        return partyEntities;
    }

    public void setPartyEntities(Set<MasterAgreementPartyEntity> partyEntities) {
        this.partyEntities = partyEntities;
    }
}
