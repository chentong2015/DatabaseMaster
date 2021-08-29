package mysql_master;

// 基础优化策略：数据库表优化，索引优化，SQL慢查询的优化
// 高级优化策略: 分布式缓存，CDN，数据库读写分离(CopyOnWrite思想)
public class MysqlOptimization {

    // 数据库表优化
    // 1. 表的字段尽可能用NOT NULL
    // 2. 字段长度固定的表查询会更快
    // 3. 把数据库的大表按时间或一些标志分成小表
    // 4. 将表拆分: 垂直拆分和水平拆分

    // 索引优化：查看索引规范
    // 一般应用系统中数据的查询操作占有很高的比例，对查询语句的优化是核心，也即通过索引快速查询

    // TODO: SQL慢查询的优化 >> SQL设计规范
    // 1. 查找和判断慢查询
    //   slow_query_log 捕获低效sql, 通过参数on来捕获执行时间
    //   ong_query_time 当SQL语句执行时间超过此数值时，就会被记录到日志中，建议设置为1或者更短
    //   slow_query_log_file 记录日志的文件名
    //   log_queries_not_using_indexes 这个参数设置为ON，可以捕获到所有未使用索引的SQL语句
    //
    //   where条件单表查，锁定最小返回记录表
    //   explain查看执行计划
    //   order by limit 形式的sql语句让排序的表优先查
    //   了解业务方使用场景
    // 2. 优化原则
    //   2.1 查询时尽量写全字段名，避免使用*查询
    //   2.2 大部分情况连接效率远大于子查询
    //   2.3 多使用explain和profile分析查询语句
    //   2.4 查看慢查询日志，找出执行时间长的sql语句优化
    //   2.5 多表连接时，尽量小表驱动大表，即小表join大表
    //   2.6 在千万级分页时使用limit
    //   2.7 对于经常使用的查询，可以开启缓存
}
