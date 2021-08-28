
public class BaseHibernateJPA {

    // Java Persistence API (JPA)
    // EntityManagerFactory -> EntityManager -> EntityTransaction / Query
    // 支持乐观锁和悲观锁 https://www.objectdb.com/java/jpa/persistence/lock#Optimistic_Locking

    // Hibernate: ORM(Object-Relational mapping)解决方案
    // 1. 将关系型数据库中的行数据映射成一个对象(对应的fields)，"数据表行" <--> "实体类"
    // 2. 直接通过调用API方法来执SQL(SQL在开发层面不可见)
    // 3. 对于需要优化SQL语句的场景不适用(比如数据量过大造成慢SQL)
    // 4. 支持乐观锁悲观锁 https://docs.jboss.org/hibernate/orm/4.0/devguide/en-US/html/ch05.html

    // Hibernate两层架构
    // 1. Hibernate Local API (功能较多): Session, SessionFactory, Transactions,,,
    // 2. Hibernate JPA   API (功能较少): EntityManagerFactory, EntityManager,,,
    // https://docs.jboss.org/hibernate/orm/5.4/quickstart/html_single/
    // http://www.hibernate.org/dtd/hibernate-mapping
}
