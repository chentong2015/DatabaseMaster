package master.hibernate.testing;

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

// TODO. 关于实体名称一致造成的mapping问题
// Retrieve PersistentClass objects without failing mappings with same entity name
// 1. [first.MyPojo] and [second.MyPojo] entities share the same JPA entity name: [MyPojo]
//    在使用默认的entity-name名称的情况下，同名的类型使用相同的JpaEntityName
// 2. If specified in the HBM entity-name attribute
//    The JPA entity name is set to be the same as the Hibernate entity name
//       如果设置了EntityName名称，则两者设置成一致的
//       if (StringHelper.isNotEmpty(jaxbEntityMapping.getEntityName())) {
//			entityName = jaxbEntityMapping.getEntityName();
//			jpaEntityName = jaxbEntityMapping.getEntityName();
//		 } else {
//			entityName = className;
//			jpaEntityName = StringHelper.unqualify( className );
//		 }
public class MasterHibernate {

    public static void main(String[] args) {
        // 1. 直接在元数据资源中添加指定的持久层class: 使用全路径添加同名的类型
        MetadataSources metadataSources = new MetadataSources();
        metadataSources.addAnnotatedClass(master.hibernate.testing.first.MyPojo.class);
        metadataSources.addAnnotatedClass(master.hibernate.testing.second.MyPojo.class);

        // 2. 这里配置的Properties信息必须和指定的数据库对应
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, PostgreSQLDialect.class.getName());

        BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().build();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
                .applySettings(properties)
                .build();

        // TODO. 使用Metadata来检索PersistentClass持久层对象(不需要构建SessionFactory)
        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder(standardServiceRegistry);
        Metadata metadata = metadataBuilder.build();
        Iterator<PersistentClass> classMappings = metadata.getEntityBindings().iterator();
        while (classMappings.hasNext()) {
            System.out.printf(classMappings.next().getEntityName());
            System.out.printf(classMappings.next().getJpaEntityName());
        }
    }
}
