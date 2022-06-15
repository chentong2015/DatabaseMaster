package com.hibernate5.testing;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.mapping.PersistentClass;

import java.util.Iterator;
import java.util.Properties;

public class HibernateMetadataSources {

    // TODO. 使用MetadataBuilder来构建SessionFactory，在构建的过程中可以配置自定义 
    public void testMetadataBuilder() {
        // 配置Properties信息必须和指定的数据库对应
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, PostgresPlusDialect.class.getName());
        BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().build();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
                .configure()
                .applySettings(properties)
                .build();
        // TODO. 1. 从Configuration中获取SessionFactory
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory(standardServiceRegistry);


        // 元数据资源中添加指定的持久层class: 使用全路径添加同名的类型
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        metadataSources.addAnnotatedClass(com.hibernate5.testing.package1.MyEntity.class);
        metadataSources.addAnnotatedClass(com.hibernate5.testing.package2.MyEntity.class);
        metadataSources.addPackage("master.hibernate6.testing");
        metadataSources.addResource("demo.hbm.xml");

        // 使用Metadata来检索PersistentClass持久层对象
        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();
        Metadata metadata = metadataBuilder.build();
        Iterator<PersistentClass> classMappings = metadata.getEntityBindings().iterator();
        while (classMappings.hasNext()) {
            System.out.printf(classMappings.next().getEntityName());
            System.out.printf(classMappings.next().getJpaEntityName());
        }

        // TODO. 2. 从Metadata中获取session factory
        SessionFactory sessionFactory1 = metadata.buildSessionFactory();
    }
}
