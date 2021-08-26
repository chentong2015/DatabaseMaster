package mysql_basics;

// TODO: 为什么要禁止多表使用join? 如果join查询没有写好，根据底层表关联的算法，导致运算量非常庞大
// MySQL表关联常见的两种算法
// 1. Nested Loop Join
//    从一张表(驱动表)循环读行，然后根据关联字段从另外一张表(被驱动表)提取满足的行，然后取出两个表的结果合集
// 2. Block Nested Loop Join
//    把原始驱动表的数据读取到join_buffer中，然后扫描被驱动表，从被驱动表中提取每一行和join_buffer中的数据对比
public class MysqlMaster {

}
