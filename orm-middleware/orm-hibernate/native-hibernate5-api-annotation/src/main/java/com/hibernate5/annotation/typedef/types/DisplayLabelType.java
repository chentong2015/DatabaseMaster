package com.hibernate5.annotation.typedef.types;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.StringTypeDescriptor;

// 自定义单个列的基本类型，适用于v5低版本
public class DisplayLabelType extends AbstractSingleColumnStandardBasicType<String> {

    public static final DisplayLabelType INSTANCE = new DisplayLabelType();

    private DisplayLabelType() {
        // super(StringColumnTypeDescriptor.INSTANCE, StringTypeDescriptor.INSTANCE);
        super(null, StringTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "display-label";
    }
}
