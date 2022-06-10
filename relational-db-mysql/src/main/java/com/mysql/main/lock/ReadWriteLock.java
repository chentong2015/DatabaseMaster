package com.mysql.main.lock;

// 操作角度:
//   1. 读锁(限制更新，不限制读)                              > 共享锁 shared locks
//   2. 写锁(限制更新和读，对于不会加锁的操作Select，不会阻塞冲突) > 排他锁 exclusive locks
// 粒度范围:
//   1. 表锁: READ [LOCAL] lock + [LOW_PRIORITY] WRITE lock
//      mysql> LOCK TABLES t1 READ;
//      mysql> SELECT COUNT(*) FROM t1;
//      mysql> SELECT COUNT(*) FROM t2; Session只能访问锁定的表, t2也需要用LOCK TABLES申明
//      mysql> UNLOCK TABLES;
//   2. 行锁
//      lock_rec_not_cap 单个行记录上的锁
//      lock_gap         间隙锁，锁定一个区间范围(行旁边的间隙)，但不包括记录本身。目的是为了防止同一个事务的两次当前读，造成幻读
//      lock_ordinary    锁定记录本身，并且锁定上下两个间隙区间范围，目的是解决幻读问题
public class ReadWriteLock {
 
    // TODO: 锁必须在事务中，事务结束时，锁自动释放掉
    // select: 不加锁，没有锁的冲突，可以直接读
    // delete: 删除数据时，先对记录加"写锁"，然后再做删除操作(版本链上添加flag)
    // insert: 插入数据时，先加"隐式锁"，在这条插入记录的事务提交前，保证不被被的事务访问到
    // update: 如果更新的列导致存储空间变化，则先加写锁然后修改. 反之先加写锁，删除之后再重新insert记录

    // 添加行锁，数据库会排队执行线程
    // update product set stock=stock-1 where id=#{id} and stock > 0;

    // TODO: 读锁, 只有和读锁不冲突
    // #Transaction100                           #Transaction
    // begin;                                    begin;
    // select ... lock in share mode;
    //                                           select ... lock in share mode; 可以添加读锁
    //                                           select ... for update; 不能添加写锁，会阻塞 !!
    //                                           select ...; 可以查询
    // select ... for update; 不能添加写锁(因为前面两个事务相当于已经加了两把读锁了)
    // commit;

    // TODO: 写锁, 和读写锁都冲突
    // #Transaction100                           #Transaction
    // begin;                                    begin;
    // select ... for update;
    //                                           select ... for update; 不能再加写锁
    //                                           update ...; 不能修改数据，会阻塞
    //                                           select ...; 可以查询，能直接读，不涉及锁
    // select ... lock in share mode; 不能再加写锁

    // 1. REPEATABLE-READ 可重复读隔离级别 TODO: 锁住查询出来的行数据
    //    防止幻读：Session1所操作(查询或更新)的数据范围内，不受到Session2插入数据的影响
    //    2.1 如果操作走索引，则把索引操作范围内的"间隙(索引会排序，出现范围)"锁住  >> 锁和Index的关系
    //    2.2 如果操作不走索引，则把整个表的所用行，同时把所有的间隙都给锁住
    //    #Session1                                 #Session2
    //    begin;                                    begin;
    //    select * form t1 where a>1 for update;
    //    >> ok
    //                                              insert into t1(b,c,d,e) values(2,5,9,"f1);
    //                                              由于a是自增的主键，Session1对a>1的全部范围都加了一把写锁
    //                                              在可重复读隔离级别下，所有结果"间隙"范围的数据都不可做更新操作，无法插入 !!
    //    commit;                                   commit;

    //    #Session1                                 #Session2
    //    begin;                                    begin;
    //    select * form t1 where c='1' for update;
    //    >> ok
    //                                              select * from t1 where a=2 for update;
    //                                              由于c不是走的索引，则会把全表的行都锁住，包括间隙的操作
    //    commit;                                   commit;

    // 2. Read Committed 读可提交隔离级别 TODO: 只锁住查询出来的行数据
    //    #Session1                                 #Session2
    //    begin;                                    begin;
    //    select * form t1 where a=1 for update;
    //    >> ok                                     select * form t1 where a=1 for update; 阻塞
    //                                              select * form t1 where a=2 for update; a是主键，Session1只锁定"指定行"
    //                                              >> ok
    //    commit;                                   commit;

    //    #Session1                                 #Session2
    //    begin;                                    begin;
    //    select * form t1 where b=1 for update;
    //    >> ok                                     select * form t1 where b=1 for update; 阻塞
    //                                              select * form t1 where b=2 for update; b是Index锁，Session1只锁定"指定行"
    //                                              >> ok
    //    commit;                                   commit;

    //    #Session1                                 #Session2
    //    begin;                                    begin;
    //    select * form t1 where c=1 for update;
    //    >> ok                                     select * form t1 where c=1 for update; 阻塞
    //                                              select * form t1 where a=6 for update; c是普通字段
    //                                              >> ok  如果所查找的行不在Session1锁定行中，则不会阻塞
    //    commit;                                   commit;
}
