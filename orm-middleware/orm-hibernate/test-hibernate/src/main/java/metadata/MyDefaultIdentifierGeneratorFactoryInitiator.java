package metadata;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class MyDefaultIdentifierGeneratorFactoryInitiator implements StandardServiceInitiator<MutableIdentifierGeneratorFactory> {

    @Override
    public Class<MutableIdentifierGeneratorFactory> getServiceInitiated() {
        return MutableIdentifierGeneratorFactory.class;
    }

    @Override
    public MutableIdentifierGeneratorFactory initiateService(Map configurationValues, ServiceRegistryImplementor registry) {
        return new MyDefaultIdentifierGeneratorFactory();
    }
}

