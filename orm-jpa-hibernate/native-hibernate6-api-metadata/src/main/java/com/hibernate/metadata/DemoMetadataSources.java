package com.hibernate.metadata;

import com.hibernate.metadata.config.MyDialectResolver;
import com.hibernate.metadata.config.MyPhysicalNamingStrategy;
import com.hibernate.metadata.entity.BaseJpaMetadata;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.hibernate.mapping.PersistentClass;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

// 1. 注入Java Entity Class成PersistentClass持久层的对象
// 2. 注入Xml Entity Mapping文件中的成PersistentClass持久层的对象
public class DemoMetadataSources {

    public static void main(String[] args) {
        BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().build();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
                .applySettings(getDbSettings())
                .configure()
                .build();

        // TODO. MetadataSources只是提供一个资源的容器
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        // 1. 类型名称如果冲突，需要使用全路径形式
        metadataSources.addAnnotatedClass(BaseJpaMetadata.class);
        // 2. 添加到xmlBindings列表中
        InputStream inputStream = DemoMetadataSources.class.getResourceAsStream("/metadata/metadata.hbm.xml");
        metadataSources.addInputStream(inputStream);

        // TODO. 使用MetadataBuilder构造Metadata配置的源数据信息
        // 使用Metadata来检索PersistentClass持久层对象: 其两个属性在加载时会被赋值 !!
        Metadata metadata = metadataSources.getMetadataBuilder().build();
        Iterator<PersistentClass> classMappings = metadata.getEntityBindings().iterator();
        while (classMappings.hasNext()) {
            PersistentClass persistentClass = classMappings.next();
            System.out.println(persistentClass.getEntityName());
            System.out.println(persistentClass.getJpaEntityName());
        }
    }

    // TODO. 完全等效于hibernate xml config文件的配置, 同时去掉entity xml mapping文件 !!
    private static Properties getDbSettings() {
        Properties properties = new Properties();
        // DB连接的信息
        properties.setProperty(AvailableSettings.URL, "");
        properties.setProperty(AvailableSettings.USER, "");
        properties.setProperty(AvailableSettings.PASS, "");
        // 配置：根据注入的entity class来自动创建DB中的tables
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create");

        // Allow the use of getCurrentSession()
        properties.setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, ThreadLocalSessionContext.class.getName());
        properties.setProperty(AvailableSettings.AUTOCOMMIT, "false");
        // properties.setProperty(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "false");
        // properties.setProperty(AvailableSettings.NATIVE_EXCEPTION_HANDLING_51_COMPLIANCE, "true");
        properties.setProperty(AvailableSettings.DIALECT_RESOLVERS, MyDialectResolver.class.getName());
        properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY, MyPhysicalNamingStrategy.class.getName());
        return properties;
    }

    // 从metadata中获取session，执行数据库的相关操作
    private Session getSessionFromMetadata(Metadata metadata) {
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        return sessionFactory.openSession();
    }
}
