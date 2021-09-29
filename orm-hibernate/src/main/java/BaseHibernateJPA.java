public class BaseHibernateJPA {

    // Java Persistence API (JPA)
    // EntityManagerFactory -> EntityManager -> EntityTransaction / Query
    // 支持乐观锁和悲观锁 https://www.objectdb.com/java/jpa/persistence/lock#Optimistic_Locking

    // Hibernate: ORM(Object-Relational mapping)
    // 两层架构:
    // 1. Hibernate Local API (功能较多): Session, SessionFactory, Transactions,,,
    // 2. Hibernate JPA   API (功能较少): EntityManagerFactory, EntityManager,,,
    //    https://docs.jboss.org/hibernate/orm/5.4/quickstart/html_single/
    //    http://www.hibernate.org/dtd/hibernate-mapping
    //
    // 框架特点:
    // 1. 将关系型数据库中的行数据映射成一个对象(对应的fields)，"数据表行" <--> "实体类"
    // 2. 通过@Query("query")自定义SQL语句，直接嵌入到源代码中，更新则需要重新编译
    // 3. 不太适合于需要优化SQL语句的场景
}
