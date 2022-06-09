package master.hibernate6.testing;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.mapping.PersistentClass;

import java.util.Iterator;
import java.util.Properties;

public class HibernateMetadataSources {

    // TODO. 使用MetadataBuilder来构建SessionFactory，在构建的过程中可以配置自定义 
    public static void main(String[] args) {
        // 1. 直接在元数据资源中添加指定的持久层class: 使用全路径添加同名的类型
        MetadataSources metadataSources = new MetadataSources();
        metadataSources.addAnnotatedClass(master.hibernate6.testing.package1.MyPojo.class);
        metadataSources.addAnnotatedClass(master.hibernate6.testing.package2.MyPojo.class);
        metadataSources.addPackage("master.hibernate6.testing");
        metadataSources.addResource("demo.hbm.xml");

        // 2. 这里配置的Properties信息必须和指定的数据库对应
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, PostgreSQLDialect.class.getName());

        BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().build();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
                .applySettings(properties)
                .build();

        // TODO. 使用Metadata来检索PersistentClass持久层对象
        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder(standardServiceRegistry);
        Metadata metadata = metadataBuilder.build();
        Iterator<PersistentClass> classMappings = metadata.getEntityBindings().iterator();
        while (classMappings.hasNext()) {
            System.out.printf(classMappings.next().getEntityName());
            System.out.printf(classMappings.next().getJpaEntityName());
        }

        // 从Metadata中获取session factory
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        Session session = sessionFactory.openSession();
    }
}
