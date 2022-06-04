package com.mysql.main.lock;

// 从锁的性能角度: 乐观锁 & 悲观锁
public class OptimisticPessimisticLock {

    // 1. 乐观锁: 总是认为不会产生并发的问题
    //    1.1 取数据，则认为不会由其他的线程对数据进行修改
    //    1.2 更新数据，则会判断其他线程在这之前是否对数据进行了修改
    //        TODO: CAS机制，添加版本号，或者时间戳
    //        > 通过时间控制: 如果有多个操作并行更改，则只有一个时间能够匹配上，并且进行更改
    //          update user_info set password=‘somelog‘
    //          where username=‘somelog‘ and time=‘2018-07-11‘;
    //        > 通过版本控制：先计算更新后的结果和更新前的初始版本号(更新一次则累加版本号)
    //          update id_generator set current_max_id=#{newMaxId}, version=version+1
    //          where version=#{version};
    // 2. 悲观锁: 总是认为会产生并发的问题
    //    2.1 每次取或更新数据的时候，数据都会被别的线程更改
    //        TODO: 对session添加读锁或者写锁
}
