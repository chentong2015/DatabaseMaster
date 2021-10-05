package mysql_basics;

// MySQL数据库：
// 1. Oracle旗下免费DB
// 2. MySQL数据实际存储在安装目录下/data/数据库名称/...(一个库对于一个文件夹)
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

    // 4. Blob & Clob
    //    Blob: Binary Large Object
    //    Clob: Character Large Object, like VARCHAR, only bigger
    //    NCLOB: like NATIONAL CHAR VARYING, only bigger
    // 支撑大文本的数据量
    // CREATE TABLE t (s1 CLOB (1K)) /* size = 1 kilobyte */
    // CREATE TABLE t (s1 CLOB (1M)) /* size = 1 megabyte */
    // CREATE TABLE t (s1 CLOB (1G)) /* size = 1 gigabyte */

    // TODO: 为什么要禁止多表使用join? 如果join查询没有写好，根据底层表关联的算法，导致运算量非常庞大
    // MySQL表关联常见的两种算法
    // 1. Nested Loop Join
    //    从一张表(驱动表)循环读行，然后根据关联字段从另外一张表(被驱动表)提取满足的行，然后取出两个表的结果合集
    // 2. Block Nested Loop Join
    //    把原始驱动表的数据读取到join_buffer中，然后扫描被驱动表，从被驱动表中提取每一行和join_buffer中的数据对比
}
