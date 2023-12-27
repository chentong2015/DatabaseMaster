package metadata;

import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;

public class MyDefaultIdentifierGeneratorFactory extends DefaultIdentifierGeneratorFactory {

    //For sybase we use the native strategy (identity)
    @Override
    public Class getIdentifierGeneratorClass(String strategy) {
        if ("id_generator.SequenceIdGenerator".equals(strategy)
                && super.getDialect().toString().contains("Sybase")) {
            return super.getIdentifierGeneratorClass("native");
        } else {
            return super.getIdentifierGeneratorClass(strategy);
        }
    }
}
