package multi_id_class.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

// @IdClass 将Class bean作为表的主键，其中包含多个联合的id
@Entity(name = "multi_id.TradeRefreshAuditRecordDTO")
@IdClass(TradeRefreshAuditRecordId.class)
@Table(name = "TRADE_REFRESH_AUDIT_DBF")
public class TradeRefreshAuditRecordDTO {

    @Id
    @Column(name = "M_TRADE_NUMBER", nullable = false, columnDefinition = "numeric", length = 10)
    private Long tradeNumber;

    @Column(name = "M_STATUS", nullable = false, columnDefinition = "char(20)")
    // @Type(type = "char-string")
    private String status;

    @Id
    @Column(name = "M__DT_TS", nullable = false, columnDefinition = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Id
    @Column(name = "M_SOURCE", nullable = false, columnDefinition = "numeric", length = 1)
    private int source;

    @Column(name = "M_AUDIT_KEY", nullable = false, columnDefinition = "numeric", length = 10)
    private Long auditKey;

    public Long getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(Long tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Long getAuditKey() {
        return auditKey;
    }

    public void setAuditKey(Long auditKey) {
        this.auditKey = auditKey;
    }

    public TradeRefreshAuditRecordId getId() {
        return new TradeRefreshAuditRecordId(tradeNumber, timestamp, source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || (getClass() != o.getClass()))
            return false;

        TradeRefreshAuditRecordDTO that = (TradeRefreshAuditRecordDTO) o;

        if (source != that.source)
            return false;
        if (!tradeNumber.equals(that.tradeNumber))
            return false;
        if (!status.equals(that.status))
            return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeNumber, source, timestamp);
    }
}
