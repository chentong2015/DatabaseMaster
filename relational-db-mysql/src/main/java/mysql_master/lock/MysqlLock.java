package mysql_master.lock;

// MySQL的锁分类
// 1. 操作:
//    读锁(限制更新，不限制读) > 共享锁
//    写锁(限制更新和读，对于不会加锁的操作Select，不会阻塞冲突) > 排他锁
// 2. 性能: 乐观锁，悲观锁
// 3. 粒度: 表锁，行锁
// 4. 其他: 间隙锁，临键锁
public class MysqlLock {

    // select: 不加锁
    // delete: 删除数据时，先对记录加写锁，然后再做删除操作(版本链上添加flag)
    // insert: 插入数据时，先加"隐式锁"，在这条插入记录的事务提交前，保证不被被的事务访问到
    // update: 如果更新的列导致存储空间变化，则先加写锁然后修改. 反之，先加写锁，删除之后再重新insert记录

    // TODO: 读锁的使用
    // #Transaction100                     #Transaction
    // begin;                              begin;
    // select ... lock in share mode;
    //                                     select ... lock in share mode; 可以添加读锁
    //                                     select ... for update; 不能添加写锁，会阻塞 !!
    //                                     select ...; 可以直接查询
    // select ... for update; 不能添加写锁(因为前面两个事务相当于已经加了两把读锁了)

    // TODO: 写锁的使用
    // #Transaction100                     #Transaction
    // begin;                              begin;
    // select ... for update;
    //                                     select ... for update; 不能再加写锁
    //                                     update ...; 不能修改数据
    //                                     select ...; 可以查询，能直接读，不涉及锁
    // select ... lock in share mode; 不能再加写锁 ???

    // https://www.bilibili.com/video/BV1Rf4y1y7xE?p=99
}
