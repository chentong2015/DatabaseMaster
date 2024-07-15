package com.entity.manager.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// EntityManager提供的公共API操作
public class EntityManagerHandler {

    // TODO. 必须使用persistence.xml文件中配置的持久层单元名称
    // The Persistence class looks for META-INF/persistence.xml in the classpath.
    // Exception in thread "main" java.lang.NoClassDefFoundError: javax/xml/bind/JAXBException
    private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("base.jpa.snapshot");

    /**
     * Static method returning EntityManager.
     *
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        // 通过EntityManagerFactory来获取EntityManager
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}
