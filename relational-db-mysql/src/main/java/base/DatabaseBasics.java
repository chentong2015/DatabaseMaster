package base;

public class DatabaseBasics {

    // MySQL的运行原理 https://juejin.cn/post/6969839499731795999
    
    /* TODO: 基本数据类型的理解
     * Float、Decimal 存储金额的区别？
     * Datetime、Timestamp 存储时间的区别？
     * Char、Varchar、Varbinary 存储字符的区别？
     */

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

    /* 创建View视图，并从视图中查询数据 ==> 视图数据的变化和原表数据的变化相互影响 !!
     * CREATE VIEW view_name AS
     * SELECT column1, column2 ,,,
     * FROM old_table_name;
     *
     * SELECT * FROM view_name; 视图是基于SQL语句结果表上的一个虚拟表
     * ALTER VIEW view_name;    修改视图
     * DROP VIEW view_name;     删除视图
     */
}
