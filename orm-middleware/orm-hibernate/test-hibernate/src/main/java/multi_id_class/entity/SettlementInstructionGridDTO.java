package multi_id_class.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "multi_id_class.entity.SettlementInstructionGridDTO")
@Table(name = "SI_GRID_DBF")
public class SettlementInstructionGridDTO {

    @Id
    @Column(name = "M_TRN_NB", nullable = false, columnDefinition = "numeric", length = 10)
    private Long tradeExtensionNumber;

    @Column(name = "M_PORTFOLIO", nullable = false, columnDefinition = "numeric", length = 10)
    private Long portfolioReference;

    @Column(name = "M_REF_N", nullable = false, columnDefinition = "numeric", length = 10)
    private Long referenceForNostro;

    @Column(name = "M_REF_V", nullable = false, columnDefinition = "numeric", length = 10)
    private Long referenceForVostro;

    @Column(name = "M_DATE", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "M_CURRENCY", nullable = false, columnDefinition = "char(3)", length = 3)
    private String currency;

    @Column(name = "M_FLOW_TYPO0", nullable = false, columnDefinition = "char(4)", length = 4)
    private String flowTypology0;

    @Column(name = "M_PR", nullable = false, columnDefinition = "numeric", length = 1)
    private Integer payReceive;

    @Column(name = "M_NATURE", nullable = false, columnDefinition = "numeric", length = 1)
    private Integer nature;

    @Column(name = "M_CST_N", nullable = false, columnDefinition = "numeric", length = 1)
    private Integer customizedNostro;

    @Column(name = "M_CST_V", nullable = false, columnDefinition = "numeric", length = 1)
    private Integer customizedVostro;

    @Column(name = "M_SPE_N", nullable = false, columnDefinition = "numeric", length = 1)
    private Integer specificNostro;

    @Column(name = "M_SPE_V", nullable = false, columnDefinition = "numeric", length = 1)
    private Integer specificVostro;

    @Column(name = "M_SI_TYPE", nullable = false, columnDefinition = "numeric", length = 1)
    private Integer siType;

    @Column(name = "M_STL_METHOD", nullable = false, columnDefinition = "char(20)", length = 20)
    private String settlementMethod;

    @Column(name = "M_CTP", nullable = false, columnDefinition = "char(15)", length = 15)
    private String counterparty;
}
