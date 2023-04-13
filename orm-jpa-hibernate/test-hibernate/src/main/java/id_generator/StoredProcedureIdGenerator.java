/**
 * Copyright Murex S.A.S., 2003-2020. All Rights Reserved.
 * <p>
 * This software program is proprietary and confidential to Murex S.A.S and its affiliates ("Murex") and, without limiting the generality of the foregoing reservation of rights, shall not be accessed, used, reproduced or distributed without the
 * express prior written consent of Murex and subject to the applicable Murex licensing terms. Any modification or removal of this copyright notice is expressly prohibited.
 */
package id_generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/* 使用自定义的ID生成器，设置到id主键上面
   @Id
   @GeneratedValue(generator = "CollateralRunDtoGenerator")
   @GenericGenerator(
      name = "CollateralRunDtoGenerator",
      strategy = "StoredProcedureIdGenerator",
      parameters = {
         @Parameter(name = "UID1", value = "COLLATERAL"),
         @Parameter(name = "UID2", value = "COLLAT_RUN")
      }
   )
   @Column(name = "COLLATERAL_RUN_ID")
   private Long id;
 */
public final class StoredProcedureIdGenerator implements IdentifierGenerator {

    public static final String FIELD1 = "UID1";
    public static final String FIELD2 = "UID2";
    public static final String RANGE_SIZE_FIELD = "COUNT";
    public static final String LOGICAL_DB_FIELD = "LOGICAL_DB";

    @Override
    public void configure(Type type, Properties props, ServiceRegistry serviceRegistry) throws MappingException {
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return generate(session, object);
    }

}
