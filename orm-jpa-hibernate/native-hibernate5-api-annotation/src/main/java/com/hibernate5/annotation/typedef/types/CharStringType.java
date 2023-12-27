package com.hibernate5.annotation.typedef.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

// 1. 使用MetadataBuilder来注入自定的类型
/* ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().build();
   MetadataSources sources = new MetadataSources( standardRegistry );
   MetadataBuilder metadataBuilder = sources.getMetadataBuilder();
   metadataBuilder.applyBasicType( CharStringType.INSTANCE );

   Metadata metadata = metadataBuilder.build();
   SessionFactory sessionFactory = innerMetadata.buildSessionFactory();
 */
public class CharStringType extends AbstractSingleColumnStandardBasicType<String> {

    public CharStringType(SqlTypeDescriptor sqlTypeDescriptor, JavaTypeDescriptor<String> javaTypeDescriptor) {
        super(sqlTypeDescriptor, javaTypeDescriptor);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object resolve(Object value, SharedSessionContractImplementor session, Object owner, Boolean overridingEager) throws HibernateException {
        return super.resolve(value, session, owner, overridingEager);
    }
}
