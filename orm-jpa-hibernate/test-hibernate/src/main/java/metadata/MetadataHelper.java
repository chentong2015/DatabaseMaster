package metadata;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.*;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.service.Service;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;
import java.util.Properties;

public class MetadataHelper {

    private static BootstrapServiceRegistry bootstrapServiceRegistry;
    private static StandardServiceRegistryBuilder standardServiceRegistryBuilder;
    private static MetadataSources metadataSources = new MetadataSources();

    public static void main(String[] args) {
        Metadata metadata = getMetadata();
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        Session session = sessionFactory.openSession();
        System.out.println("done" + session.toString());
    }

    private static Metadata getMetadata() {
        bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().build();
        standardServiceRegistryBuilder = new StandardServiceRegistryBuilder(bootstrapServiceRegistry);

        // 配置Properties信息必须和指定的数据库对应
        Properties properties = new Properties();
        properties.put(AvailableSettings.URL, "jdbc:postgresql://localhost:5432/my_database");
        properties.put(AvailableSettings.USER, "postgres");
        properties.put(AvailableSettings.PASS, "admin");
        properties.put(AvailableSettings.DIALECT, PostgresPlusDialect.class.getName());
        standardServiceRegistryBuilder.applySettings(properties);

        standardServiceRegistryBuilder.addInitiator(new MyDefaultIdentifierGeneratorFactoryInitiator());
        // 自定义实现Service Initiator并且注入
        standardServiceRegistryBuilder.addInitiator(new StandardServiceInitiator() {
            @Override
            public Service initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
                return null;
            }

            @Override
            public Class getServiceInitiated() {
                return null;
            }
        });


        StandardServiceRegistry standardServiceRegistry = standardServiceRegistryBuilder.build();
        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder(standardServiceRegistry);
        return metadataBuilder.build();
    }
}
