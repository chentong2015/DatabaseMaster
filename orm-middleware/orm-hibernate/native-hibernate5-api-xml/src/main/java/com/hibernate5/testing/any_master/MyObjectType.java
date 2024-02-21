package com.hibernate5.testing.any_master;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.AnyType;
import org.hibernate.type.BasicType;
import org.hibernate.type.SerializableType;
import org.hibernate.type.StringType;

public class MyObjectType extends AnyType implements BasicType {

    public static final MyObjectType INSTANCE = new MyObjectType();

    private MyObjectType() {
        super(StringType.INSTANCE, SerializableType.INSTANCE);
    }

    @Override
    public String getName() {
        return "object";
    }

    @Override
    public String[] getRegistrationKeys() {
        return new String[]{getName(), Object.class.getName()};
    }

    @Override
    public Object resolve(Object value, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        try {
            return super.resolve(value, session, owner);
        } catch (HibernateException exception) {
            System.out.println("warning: No row with the given identifier exists.");
            return null;
        }
    }
}
