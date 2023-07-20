package com.hibernate5.annotation.typedef.types;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.DataHelper;
import org.hibernate.type.descriptor.java.StringTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

import java.io.Reader;
import java.sql.Clob;

// 实现DiscriminatorType，可以设置到discriminator列上
// discriminator-value="xxx">
// <discriminator type="trimmed-string" column="M_INT"/>
public class TrimmedStringType extends AbstractSingleColumnStandardBasicType<String> implements DiscriminatorType<String> {

    public static final TrimmedStringType INSTANCE = new TrimmedStringType();

    public TrimmedStringType() {
        super(VarcharTypeDescriptor.INSTANCE, TrimmedStringTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "trimmed-string";
    }

    @Override
    public String objectToSQLString(String value, Dialect dialect) {
        return '\'' + value + '\'';
    }

    @Override
    public String stringToObject(String xml) {
        return fromString(xml);
    }

    // TODO. TrimmedString类型定于作用在列字段上，查询和存储时会自动的将string.trim()尾部空格去掉
    // @Column(name = "M_DESC")
    // @Type(type = "trimmed-string")
    // private String description;
    private static class TrimmedStringTypeDescriptor extends StringTypeDescriptor {

        public static final TrimmedStringTypeDescriptor INSTANCE = new TrimmedStringTypeDescriptor();

        @Override
        public String fromString(String string) {
            return string.trim();
        }

        @Override
        public <X> String wrap(X value, WrapperOptions options) {
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return fromString((String) value);
            }
            if (value instanceof Reader) {
                return fromString(DataHelper.extractString((Reader) value));
            }
            if (value instanceof Clob) {
                return fromString(DataHelper.extractString((Clob) value));
            }
            throw unknownWrap(value.getClass());
        }
    }
}
