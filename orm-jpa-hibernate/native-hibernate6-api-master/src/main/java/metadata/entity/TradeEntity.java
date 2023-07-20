package metadata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 通常情况下，Entity是需要带有无参构造器的，否则在查询数据的时候无法构造object
// 没有无参构造器的情况下，会给出如下提示，在查询表的数据时会报错
// INFO: HHH000182: No default (no-argument) constructor for class: entity.TradeEntity (class must be instantiated by Interceptor)
@Entity(name = "metadata.entity.TradeEntity")
@Table(name = "t_trade_entity")
public class TradeEntity {

    @Id
    @Column(name = "M_REFERENCE")
    private Long reference;

    @Column(name = "M_LABEL")
    private String label;

    public TradeEntity() {
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
}
