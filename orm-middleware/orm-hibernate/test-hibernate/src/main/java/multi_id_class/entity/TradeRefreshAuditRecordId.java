package multi_id_class.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TradeRefreshAuditRecordId implements Serializable {

    private Long tradeNumber;
    private Date timestamp;
    private int source;

    public TradeRefreshAuditRecordId() {
        // no-arg constructor
    }

    public TradeRefreshAuditRecordId(Long tradeNumber, Date timestamp, int source) {
        this.tradeNumber = tradeNumber;
        this.timestamp = timestamp;
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || (getClass() != o.getClass()))
            return false;

        TradeRefreshAuditRecordId that = (TradeRefreshAuditRecordId) o;

        if (source != that.source)
            return false;
        if (!tradeNumber.equals(that.tradeNumber))
            return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeNumber, source, timestamp);
    }
}
