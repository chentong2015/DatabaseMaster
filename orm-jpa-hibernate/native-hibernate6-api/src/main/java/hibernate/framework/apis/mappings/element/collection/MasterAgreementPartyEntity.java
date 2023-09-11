package hibernate.framework.apis.mappings.element.collection;

import jakarta.persistence.*;

@Embeddable
@Access(AccessType.FIELD)
@Table(name = "MASTER_AGR_PARTY_DBF")
public class MasterAgreementPartyEntity {

    @Column(name = "M_PARTY_REF", nullable = false, columnDefinition = "numeric(10,0)", length = 10)
    private Long counterpartyReference;

    @Column(name = "M_LIST_TYPE", nullable = false, columnDefinition = "numeric(1,0)", length = 1)
    private int counterpartyType;
    
    public MasterAgreementPartyEntity() {
    }

    public MasterAgreementPartyEntity(Long counterpartyReference, int counterpartyType) {
        this.counterpartyReference = counterpartyReference;
        this.counterpartyType = counterpartyType;
    }

    public void setCounterpartyReference(Long counterpartyReference) {
        this.counterpartyReference = counterpartyReference;
    }

    public void setCounterpartyType(int counterpartyType) {
        this.counterpartyType = counterpartyType;
    }

    public Long getCounterpartyReference() {
        return counterpartyReference;
    }

    public int getCounterpartyType() {
        return counterpartyType;
    }
}
