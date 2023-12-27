package multi_id_class.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "multi_id_class.entity.TradeExtensionDTO")
@Table(name = "TRN_EXT_DBF")
public class TradeExtensionDTO {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    @Id
    @Column(name = "M_REFERENCE", nullable = false, columnDefinition = "numeric", length = 10)
    private Long reference;

    @Column(name = "M_TRADE_REF", nullable = false, columnDefinition = "numeric", length = 10)
    private Long tradeReference;

    @Column(name = "M_DATE", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "M_VERSION", nullable = false, columnDefinition = "numeric", length = 6)
    private Integer version;

}
