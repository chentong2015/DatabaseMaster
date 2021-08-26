package mysql_master.transactions;

// 1. 事务的ACID属性
//    Atomicity  原子性: 事务是一个原子执行单元，全部执行或者不执行
//    Consistent 一致性: 所有相关的数据都应用于事务的修改，避免不一致
//    Isolation  隔离性: 数据库提供的隔离机制，保证事务部受到外部并发操作影响
//    Durable    持久性: 数据修改是永久性的
// 2. 并发事务带来的问题: 更新丢失或者错写

// TODO: 解决多事务并发的策略
// 1. 事务隔离级别
// 2. MVCC机制：多个事务同时去操作数据，防止加锁的高效机制
//    ReadView机制
//    Undo回滚链
public class Transactions {

    // 事务在执行的过程中，会生成binlog, undo log, redo log
    // 1. bin log:  存储一些执行的SQL语句  ==> 不是InnoDB中的概念，而是MySQL的概念
    // 2. undo log:
    // 3. redo log:

    // https://www.bilibili.com/video/BV1954y1h7Lw?p=25

}
