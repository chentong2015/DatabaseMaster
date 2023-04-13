package metadata;

import dialect.MyDialectResolver;
import metadata.entity.BaseEntity;
import metadata.entity.BaseJpaMetadata;
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
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.mapping.PersistentClass;

import java.io.InputStream;
import java.util.Properties;

// TODO. 使用纯JPA注解配置，完全脱离xml文件的设置
// 1. 注入Java Entity Class成PersistentClass持久层的对象
// 2. 注入Xml Entity Mapping文件中的成PersistentClass持久层的对象
public class MetadataSourcesDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().build();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
                .applySettings(getDbSettings())
                .configure()
                .build();

        // TODO. MetadataSources提供一个资源容器: 加载entity class或者hbm entity
        MetadataSources metadataSources = new MetadataSources();
        metadataSources.addAnnotatedClass(BaseJpaMetadata.class);
        metadataSources.addAnnotatedClass(BaseEntity.class.getClass());

        InputStream inputStream = MetadataSourcesDemo.class.getResourceAsStream("/metadata/metadata.hbm.xml");
        metadataSources.addInputStream(inputStream);

        // TODO. 使用MetadataBuilder构造Metadata配置的源数据信息
        // 使用Metadata来检索PersistentClass持久层对象: 其两个属性在加载时会被赋值
        Metadata metadata = metadataSources.getMetadataBuilder(standardServiceRegistry).build();
        for (PersistentClass persistentClass : metadata.getEntityBindings()) {
            System.out.println(persistentClass.getEntityName());
            System.out.println(persistentClass.getJpaEntityName());
        }

        // 从Metadata中获取Session
        try (SessionFactory sessionFactory = metadata.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
        }
    }

    // TODO. 完全等效于hibernate xml config文件的配置, 同时去掉entity xml mapping文件
    // properties.put("hibernate.connection.datasource") 通过设置DataSource来指定连接
    private static Properties getDbSettings() {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.URL, "jdbc:postgresql://localhost:5432/my_database");
        properties.setProperty(AvailableSettings.USER, "postgres");
        properties.setProperty(AvailableSettings.PASS, "admin");
        // 这里的配置会导致自动创建Table
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create");
        properties.setProperty(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, ThreadLocalSessionContext.class.getName());
        properties.setProperty(AvailableSettings.AUTOCOMMIT, "false");

        // 指定连接数据库的Dialect方言
        properties.setProperty("hibernate.dialect_resolvers", PostgresPlusDialect.class.getName());
        // 注入自定义的Dialect解析器和命名的策略
        properties.setProperty(AvailableSettings.DIALECT_RESOLVERS, MyDialectResolver.class.getName());
        // properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY, MyPhysicalNamingStrategy.class.getName());
        return properties;
    }
}
