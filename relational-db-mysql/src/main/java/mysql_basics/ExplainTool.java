package mysql_basics;

// Explain Tool工具: 提供MySQL执行语句的信息
// 使用explain可以模拟优化器执行SQL语句，分析查询语句或结构的性能瓶颈
// 在select之前添加explain关键字，MySQL会在查询上设置一个标记，执行查询会返回执行计划的信息，而不是执行SQLy语句
// https://dev.mysql.com/doc/refman/5.7/en/explain-output.html

// > explain select * from film where id = 10;
//   id select_type table type  key possible_keys ref   rows filtered Extra
//   1  SIMPLE      film  const null PRIMARY       const 1    100      null  不同的值代表不同的执行情况
//                              key值为空，表示查询没有走索引
public class ExplainTool {

}
