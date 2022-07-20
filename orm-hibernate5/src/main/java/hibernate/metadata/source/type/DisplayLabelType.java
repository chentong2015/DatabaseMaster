package hibernate.metadata.source.type;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.StringTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

// 自定义的列的type
public class DisplayLabelType extends AbstractSingleColumnStandardBasicType<String> {

    public static final DisplayLabelType INSTANCE = new DisplayLabelType();

    private DisplayLabelType() {
        super(VarcharTypeDescriptor.INSTANCE, StringTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "display-label";
    }
}
