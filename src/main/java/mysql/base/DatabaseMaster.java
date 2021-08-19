package mysql.base;

public class DatabaseMaster {


    // Q1. MySQL有哪些存储引擎啊？有何区别？
    // 1. 数据库往往是一个比较丰富完整的系统, 提供了SQL查询语言，事务和水平扩展等支持，底层往往会使用某种存储引擎
    // 2. 存储引擎则是小而精, 纯粹专注于单机的读/写/存储
    // MyISAM存储引擎、innoDB存储引擎、MEMORY存储引擎、ARCHIVE存储引擎
    // 存储引擎会进行哪些自动优化？https://zhuanlan.zhihu.com/p/50564425

    // 什么是MySQL隔离级别？
    // 对Explain参数及重要参数的理解？
    // 二叉树的装置是什么？
    // B+tree 如何进行优化？索引遵循哪些原则？
    // 谈谈对Innodb事务的理解？
    // 说说数据库事务特点及潜在问题？
    // 有多少种事务失效的场景，如何解决？

    // 在执行transactions操作的时候所使用的两种锁的策略: 乐观锁，悲观锁 !!!
    // https://docs.oracle.com/javaee/7/tutorial/persistence-locking001.htm
    // https://www.objectdb.com/java/jpa/persistence/lock#Optimistic_Locking
    // https://docs.jboss.org/hibernate/orm/4.0/devguide/en-US/html/ch05.html
}
