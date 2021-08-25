package mysql_basics;

// TODO: 为什么要禁止多表使用join?
//       如果join查询没有写好，根据底层的算法，导致的运算量非常庞大
// MySQL表关联常见的两种算法
// 1. Nested Loop Join
//    从一张表(驱动表)循环读行，然后根据关联字段从另外一张表(被驱动表)提取满足的行，然后取出两个表的结果合集
// 2. Block Nested Loop Join
//    把原始驱动表的数据读取到join_buffer中，然后扫描被驱动表，从被驱动表中提取每一行和join_buffer中的数据对比
public class MysqlMaster {

    // MySQL索引面试题全套视频教程|MySQL原理与优化实战案例讲解 https://www.bilibili.com/video/BV1954y1h7Lw
    // 第二节 重复
    // 第三节 重复 //
    // 第四节 新的内容

    // 什么是MySQL隔离级别？
    // 对Explain参数及重要参数的理解？
    // 二叉树的装置是什么？
    // B+tree 如何进行优化？索引遵循哪些原则？
    // 谈谈对Innodb事务的理解？
    // 说说数据库事务特点及潜在问题？
    // 有多少种事务失效的场景，如何解决？
    // MySQL的运行原理 https://juejin.cn/post/6969839499731795999
    // 存储引擎会进行哪些自动优化？https://zhuanlan.zhihu.com/p/50564425

    // 什么是索引？
    // 对比一下B+树索引和 Hash索引？
    // MySQL索引类型有？
    // 如何管理 MySQL索引？
    // 索引利弊是什么及索引分类？
    // 索引与锁有什么关系？
    // 还有什么其他的索引类型，各自索引有哪些优缺点？
    // 聚簇索引和非聚簇索引的区别？
    // 到底何时索引会失效？ 为什么会出现索引失效 ?

    // 在执行transactions操作的时候所使用的两种锁的策略: 乐观锁，悲观锁 !!!
    // https://docs.oracle.com/javaee/7/tutorial/persistence-locking001.htm
    // https://www.objectdb.com/java/jpa/persistence/lock#Optimistic_Locking
    // https://docs.jboss.org/hibernate/orm/4.0/devguide/en-US/html/ch05.html
}
