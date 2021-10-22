package mysql_distributed_id;

// 使用数据库的自增ID来实现简易的"分布式ID生成器"
// 1. 数据库多主模式
// 2. 号段模式
public class MysqlAutoIncrementId {

    // CREATE DATABASE seqid;
    //
    // TODO: 修改全局的自增id的步长
    // set @@auto_increment_offset = 1;
    // set @@auto_increment_increment = 2;
    //
    // CREATE TABLE seqid.`tb_distributed_id` (
    //	 `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    //	 `stub` CHAR(10) NOT NULL DEFAULT '',
    //	 PRIMARY KEY (`id`)
    // ) COLLATE='utf8_general_ci';
    //
    // BEGIN;
    // -- 替换语句的过程: select -> delete -> insert 插入变动的数据
    // REPLACE INTO seqid.`t_distributed_id` (stub) VALUES ('anyword');
    // -- 上面的insert语句会导致`id`的值自增
    // -- 调用系统方法，取出最后一个插入的id(自增的id)
    // SELECT LAST_INSERT_ID();
    // COMMIT;

}
