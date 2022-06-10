package com.mysql.main.transaction;

// 事务的ACID属性
// 1. Atomicity  原子性: 事务是一个原子执行单元，全部执行或者不执行
// 2. Consistent 一致性: 所有相关的数据都应用于事务的修改，避免不一致
// 3. Isolation  隔离性: 并发执行的事务彼此无法看到彼此的中间状态
// 4. Durable    持久性: 数据修改是永久性的
// TODO: 在高并发场景下，要完全做到ACID非常困难，除非将所有的事务串行化，性能降低
//   根据不同的需求，数据库设计了四种隔离的级别
public class Transaction {

    // MySQL何时生成事务ID
    // 1. 在begin之后，执行第一个操作InnoDB语句表的语句时，事务才真正启动，向MySQL申请事务id，并且严格按照顺序来分配
    // 2. 只有update, delete, insert更新操作才会生成事务id，查询语句select不会有事务id

    // 事务执行基本流程
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
    //    2.1 记录Page中某地址中的一些数据进行了修改，做数据恢复会更快
    //    2.2 不能用于数据库的主从关系，不能搬redo log
    // 3. undo log:
    //    3.1 记录"反向操作"，用于事务执行的过程中回滚 insert -> delete, update -> update

    // TODO. 事务的传播机制:
    // MethodA(带事务) calls MethodB(带事务) calls MethodC(带事务)
    // 在嵌套方法调用的过程中，事务是如何在其中进行传播的，模式和路径?
    // 1. 支持MethodA中的事务
    // 2. 不支持MethodA中的事务
}
