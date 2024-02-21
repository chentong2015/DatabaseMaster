package multi_id_class.query_builder;

import multi_id_class.entity.SettlementInstructionGridDTO;
import multi_id_class.entity.TradeExtensionDTO;

public enum EntityId {

    SI_GRID(SettlementInstructionGridDTO.class),
    TRADE_EXT(TradeExtensionDTO.class);

    private final Class<?> entityClass;

    EntityId(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getEntityName() {
        return getEntityClass().getName();
    }

    public String getField(String field) {
        return this + "." + field;
    }
}
