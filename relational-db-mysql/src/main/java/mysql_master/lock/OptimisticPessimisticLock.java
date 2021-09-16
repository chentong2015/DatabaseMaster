package mysql_master.lock;

// 从锁的性能角度: 乐观锁 & 悲观锁
public class OptimisticPessimisticLock {

    // 1. 乐观锁: 总是认为不会产生并发的问题
    //    1.1 如果是取数据，则认为不会由其他的线程对数据进行修改
    //    1.2 如果是更新数据，则会判断其他线程在这之前是否对数据进行了修改
    //        TODO: CAS机制，添加版本号，或者时间戳
    //        通过时间控制，如果有多个操作并行更改，则只有一个时间能够匹配上，并且进行更改
    //        update user_info set password=‘somelog‘ where username=‘somelog‘ and time=‘2018-07-11‘;
    //
    //    1.3 案例: 添加行锁，数据库会排队执行线程
    //        update product set stock=stock-1 where id=#{id} and stock > 0;

    // 2. 悲观锁: 总是认为会产生并发的问题
    //    2.1 每次取或更新数据的时候，数据都会被别的线程更改
    //        TODO: 对session添加读锁或者写锁
}
