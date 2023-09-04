package transaction;

// 并发事务/事务失效的场景
// 0. 更新丢失: A事务提交或者撤销时，把B事务更新数据覆盖
// 1. 脏读: 读到未提交更新的数据(事务commit提交前回滚了)               ===> 需要提高隔离级别 !!
// 2. 不可重复读：一个事务范围内两个查询返回了不同的结果                 ===> MySQL设计的效果是"REPEATABLE-READ 可重复读"
// 3. 幻读: 查询表中一条数据，如果不存在就插入，并发时里面有两条相同的数据  ===> MVCC机制 + lock_ordinary锁
public class TransactionConcurrencyMvcc {

    // TODO: Multi-Version Concurrency Control 实现"可重复读"和"读可提交"两种隔离级别
    // TODO: ReadView机制 + Undo回滚链 
    // 1. 查询sql时会生成一致性视图(快照记录，并不是复制数据)，由所有未提交的事务id和已经创建的最大事务id组成
    //    [min_id,,,]max_id; 根据该一致性视图判断数据的可见性(如图)
    // 2. ReadView是针对session级别的，当session中执行select的时候，则会生成这样一个快照
    //    之后所有的select语句的执行都延用这个快照去完成"版本链比对规则"，即使换一张表select也是如此
    //
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
    //                                                                               延用之前的readview，和第一取出的数据一致               此时的readview中min_id变化，查找比对返回的结果为"2"
    //                          commit;
}
