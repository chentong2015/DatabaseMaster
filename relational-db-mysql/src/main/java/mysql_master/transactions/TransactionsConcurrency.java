package mysql_master.transactions;

// 并发事务带来的问题
// 1. 更新丢失: A事务提交或者撤销时，把B事务更新的数据覆盖
// 2. 脏读: 读到未提交更新的数据
// 3. 不可重复读：一个事务范围内两个查询返回了不同的结果
// 4. 幻读：读到已提交插入数据
public class TransactionsConcurrency {

    // 1. 事务的4种隔离级别(SQL的标准，适用不同的场景)
    //    1.1 可重复读: 查询的时候第一次记录的readview始终不变
    //        #transaction200   #transaction300    #select100
    //        begin;            begin;             begin;
    //                                             select...; readview[200,300]存储除自己以外的活跃事务ID
    //        update...生成undo log日志记录
    //                          update...追加undo log日志链条
    //                                             select...; 再次插内存中数据，发现undo log，根据记录的日志来(恢复)回到原始数据
    //                          commit;
    //                                             select...; readview[200,300] 根据隔离级别的机制，不会改变
    //        update
    //        update
    //                                             select...; readview[200,300]
    //        commit;
    //    1.2 读可提交：和可重复读类似，每次查询的时候记录的readview都会刷新
    //                直接判断undo log版本链中的头部的事务id，判断是否执行undo log链
    //    1.3 读未提交
    //    1.4 串行化 ==> 使用锁

    // TODO: MySQL使用MVCC机制实现"可重复读"和"读可提交"隔离级别
    //       使用MVCC机制+锁，解决数据"幻读"的问题

    // 多个事务同时去操作数据，防止加锁的高效机制
    //    多版本并发控制，基于undo log来实现的
    //    https://www.bilibili.com/video/BV1GV411B7fX?p=9
    //    ReadView机制
    //    Undo回滚链

}
