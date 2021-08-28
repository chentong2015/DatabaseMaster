package mysql_basics;

// TODO: 主要数据库开发常见规范
// MySQL数据库的数据实际存储在安装目录下/data/数据库名称/...(一个库对于一个文件夹)
public class BaseMysql {

    // 基本数据类型的理解
    // 1. Float、Decimal 存储金额的区别？
    //    float是浮点数，不能指定小数位，容易产生误差
    //    decimal是精确数，可以指定精度，使用于财务应用中金额的计算
    //    decimal在mysql内存是以字符串存储的，用于定义货币要求精确度高的数据

    // 2. Datetime、Timestamp 存储时间的区别？
    //    datetime保存的是绝对值不会变化, 占用8个字节, datetime支持的范围更宽
    //    timestamp会跟随设置的时区变化而变化, 占用4个字节, 更轻量，索引相对datetime更快

    // 3. Char、Varchar、Varbinary 存储字符的区别？
    //    char使用固定长度的空间进行存储，char(4)存储4个字符
    //    varchar保存可变长度的字符串，使用额外的一个或两个字节存储字符串长度
    //    binary保存二进制字符串，它保存的是字节而不是字符

    /* 从一个表中提取数据到另外一个表
     * 1. SELECT names
     *    INTO   new_table_name  在默认的file-group位置创建一个新表
     *    FROM   old_table_name
     * 2. INSERT INTO new_table_name
     *    SELECT names           选择出来的列需要和目标新表匹配
     *    FROM old_table_name
     */

    /* 如果修改已经建好的Table
     * 1. ALTER old_table_name
     *    ADD   column_name datatype;
     * 2. ALTER old_table_name
     *    ALTER COLUMN column_name datatype;
     */

    /* 如何删除已经建好的表
     * 1. DROP TABLE old_table_name;     将数据和表一同删除
     * 2. TRUNCATE TABLE old_table_name; 只清空表中的全部数据，并且只写入一次日志信息
     */

    /* 创建View视图，并从视图中查询数据: 视图数据的变化和原表数据的变化相互影响 !!
     * CREATE VIEW view_name AS
     * SELECT column1, column2 ,,,
     * FROM old_table_name;
     *
     * SELECT * FROM view_name; 视图是基于SQL语句结果表上的一个虚拟表
     * ALTER VIEW view_name;    修改视图
     * DROP VIEW view_name;     删除视图
     */

    // TODO: 为什么要禁止多表使用join? 如果join查询没有写好，根据底层表关联的算法，导致运算量非常庞大
    // MySQL表关联常见的两种算法
    // 1. Nested Loop Join
    //    从一张表(驱动表)循环读行，然后根据关联字段从另外一张表(被驱动表)提取满足的行，然后取出两个表的结果合集
    // 2. Block Nested Loop Join
    //    把原始驱动表的数据读取到join_buffer中，然后扫描被驱动表，从被驱动表中提取每一行和join_buffer中的数据对比
}
