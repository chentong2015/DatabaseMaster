package mysql_master.transaction;

// 事务操作的基本规范：
// 1. 多表写操作要保证事务完整性
//    缺省单条SQL语句就是一个事务，但是涉及多表写操作，需要显示开启事务，才能保证数据完整性
//    反例:多表操作没开启事务，可能存在A表修改成功，B表修改失败，破坏数据完整性

// 2. 多表写操作事务保证表的操作顺序，避免死锁
//    反例: X线程锁表顺序为AB，Y线程锁表顺序为BA。并发场景会导致死锁

// 3. 保持事务短小,事务占有时间越短越好
//    事务长时间锁表会严重阻塞其他SQL语句的执行
public class TransactionSpecification {

    // Java JDBC Transaction执行操作
    // 1. Turn off auto-commit: 默认是自动提交
    //    connection.setAutoCommit(false); Select查询同样需要注意事务
    // 2. Perform the SQL Operations
    // 3. If OK, Connection.commit();
    //    Else Connection.rollback();
    // 4. Turn on the auto-commit: Connection.setAutoCommit(true);
}
