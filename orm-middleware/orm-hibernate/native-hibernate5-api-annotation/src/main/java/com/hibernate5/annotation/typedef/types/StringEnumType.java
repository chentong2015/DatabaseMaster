package com.hibernate5.annotation.typedef.types;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.StringTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class StringEnumType extends AbstractSingleColumnStandardBasicType<String> {

    public static final StringEnumType INSTANCE = new StringEnumType();

    private StringEnumType() {
        super(VarcharTypeDescriptor.INSTANCE, StringTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "string-enum";
    }
}
