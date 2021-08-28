package mysql_master.transactions;

// 并发事务带来的问题，事务失效的场景
// 1. 脏读: 读到未提交更新的数据
// 2. 幻读: 读到已提交插入数据                       ===> MVCC机制 + lock_ordinary锁
// 3. 更新丢失: A事务提交或者撤销时，把B事务更新数据覆盖
// 4. 不可重复读：一个事务范围内两个查询返回了不同的结果   ===> MySQL设计的效果是"REPEATABLE-READ 可重复读"
public class TransactionsConcurrency {

    // MySQL何时生成事务ID
    // 1. 在begin之后，执行第一个操作InnoDB语句表的语句时，事务才真正启动，向MySQL申请事务id，并且严格按照顺序来分配
    // 2. 只有update, delete, insert更新操作才会生成事务id，查询语句select不会有事务id

    // TODO: 事务的4种隔离级别(SQL的标准，适用不同的场景)
    // > set session transaction isolation level read committed; 设置session transaction隔离级别
    // > select @@tx_isolation;  查看隔离级别
    //    1.1 可重复读:
    //        #transaction200       #transaction300        #select100
    //        begin;                begin;                 begin;
    //                                                     select...; readview[200,300]存储除自己以外的活跃事务ID数组
    //        update...生成undo log
    //                              update...追加undo log
    //                                                     select...; 再次查内存中数据，发现undo log，根据记录的日志来(恢复)回到原始数据
    //                              commit;
    //                                                     select...; readview[200],300 根据隔离级别的机制，不会改变
    //                                                                查询的时候第一次记录的readview始终不变
    //        update
    //                                             select...; readview[200],300
    //        commit;
    //    1.2 读可提交：和可重复读类似，每次查询的时候记录的readview都会刷新
    //                直接判断undo log版本链中的头部的事务id，判断是否执行undo log链
    //    1.3 读未提交
    //    1.4 串行化: 使用锁

    // TODO: Multi-Version Concurrency Control 实现"可重复读"和"读可提交"两种隔离级别
    // TODO: ReadView机制 + Undo回滚链
    // 1. 查询sql时会生成一致性视图(快照记录，并不是复制数据)，由所有未提交的事务id和已经创建的最大事务id组成
    //    [min_id,,,]max_id; 根据该一致性视图判断数据的可见性(如图)
    // 2. ReadView是针对session级别的，当session中执行select的时候，则会生成这样一个快照
    //    之后所有的select语句的执行都延用这个快照去完成"版本链比对规则"，即使换一张表select也是如此

    // #Transaction100          #Transactions200         #Transactions300            #Select1                                          #Select2
    // begin;                   begin;                   begin;                      begin;                                            begin;
    // update test c1=123
    //                          update test c1=666                                   select * from test; readview[100,200,300],300
    //                                                                               如果在此处执行select的时候会生成全库的快照readview
    //                                                   update account name="300"
    //                                                   commit;
    //                                                                               select name...; readview[100,200],300，返回"300"
    //                                                                               未提交的id放在内部，已经提交的放到外部
    // update account name="1"
    // update account name="2"
    //                                                                               select name...; readview[100,200],300
    // commit;
    //                          update account name="3"
    //                          update account name="4"
    //                                                                               select name...; readview[100,200],300             select name...; readview[200],300
    //                                                                               延用之前的readview，和第一取出的数据一致                此时的readview中min_id变化，查找比对返回的结果为"2"
    //                          commit;
}
