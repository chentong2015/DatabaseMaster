package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 通常情况下，Entity是需要带有无参构造器的，否则在查询数据的时候无法构造object
@Entity(name = "entity.TradeEntity")
@Table(name = "t_trade")
public class TradeEntity {

    @Id
    @Column(name = "M_NB", nullable = false, columnDefinition = "numeric(10)", length = 10)
    private Long reference;

    @Column(name = "M_COUNTRPART", nullable = false, columnDefinition = "numeric", length = 10)
    private Long counterpartyReference;

    public TradeEntity(Long reference, Long counterpartyReference) {
        this.reference = reference;
        this.counterpartyReference = counterpartyReference;
    }

    public Long getId() {
        return reference;
    }

    public Long getCounterpartyReference() {
        return counterpartyReference;
    }
}
