package com.hibernate5.testing.metadata;

import com.hibernate5.testing.metadata.entity.BaseJpaMetadata;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.PersistentClass;

import java.io.InputStream;
import java.util.Iterator;

// 测试Hibernate如何加载MetadataSources源数据的资源
// 如何将xml文件中的信息提取成PersistentClass持久层的对象
public class HibernateMetadataSources {

    public static void main(String[] args) {
        BootstrapServiceRegistry bootstrapServiceRegistry = new BootstrapServiceRegistryBuilder().build();
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder(bootstrapServiceRegistry)
                .configure()
                .build();

        // 元数据资源中添加指定的持久层class: 使用全路径添加同名的类型
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
        metadataSources.addAnnotatedClass(BaseJpaMetadata.class);
        InputStream inputStream = HibernateMetadataSources.class.getResourceAsStream("/metadata/metadata.hbm.xml");
        metadataSources.addInputStream(inputStream);

        // 使用Metadata来检索PersistentClass持久层对象
        MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();
        Metadata metadata = metadataBuilder.build();

        // PersistentClass Hibernate持久层的对象的两个属性在加载时会被赋值
        Iterator<PersistentClass> classMappings = metadata.getEntityBindings().iterator();
        while (classMappings.hasNext()) {
            PersistentClass persistentClass = classMappings.next();
            System.out.println(persistentClass.getEntityName() + "--" + persistentClass.getJpaEntityName());
        }
    }

    // 从metadata中获取session，执行数据库的相关操作
    private Session getSessionFromMetadata(Metadata metadata) {
        SessionFactory sessionFactory = metadata.buildSessionFactory();
        return sessionFactory.openSession();
    }
}
