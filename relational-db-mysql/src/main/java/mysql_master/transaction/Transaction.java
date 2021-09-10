package mysql_master.transaction;

public class Transaction {

    // 事务的ACID属性
    // 1. Atomicity  原子性: 事务是一个原子执行单元，全部执行或者不执行
    // 2. Consistent 一致性: 所有相关的数据都应用于事务的修改，避免不一致
    // 3. Isolation  隔离性: 数据库提供的隔离机制，保证事务部受到外部并发操作影响
    // 4. Durable    持久性: 数据修改是永久性的

    // 执行事务的基本流程
    // begin;
    // update        修改buffer pool中的数据
    // ...
    // // rollback   如果回滚，则需要通过undo log日志来还原
    // commit;       此时才做redo log持久化处理, 对应存储区域是Log Buffer !!

    // TODO: 事务在执行的过程中，会生成三种log
    // 1. bin log: MySQL的概念
    //    1.1 存储一些执行的SQL语句，恢复的时候需要执行语句，比redo log慢
    //    1.2 主要用在数据库主从上面，比如需要将数据库转移到新开的数据库中，则需要bin log
    // 2. redo log: InnoDB存储引擎概念  >> 在事务commit提交之后生成
    //    2.1 记录的是Page中某地址中的一些数据进行了修改，做数据恢复会更快
    //    2.2 不能用于数据库的主从关系，不能搬redo log
    // 3. undo log:
    //    3.1 记录"反向操作"，用于事务执行的过程中回滚 insert -> delete, update -> update
}
