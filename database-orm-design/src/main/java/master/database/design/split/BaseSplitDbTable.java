package master.database.design.split;

// MySQL单表存储约300-500万的数据量
// 在表的数据量上升到千万级别之后，需要分库分表进行优化处理
// 由于InnoDB存储引擎底层使用B+树来组织数据，这个会导致B+树的高度增加，存储和查询数据性能降低
public class BaseSplitDbTable {

    // 1. 为什么要分库分表
    //    对关系型数据库的存储和访问机制的补充，减少数据的压力
    // 2. 什么是分库分表
    //    增加数据库，拆分大表 >> 去中心化
    // 3. 分库分表的几种形式
    //    数据库和表的拆分都有这两种形式：
    //    3.1 垂直拆分: 指按功能模块拆分，比如分为订单库、商品库、用户库，多个数据库之间表结构不同
    //        每个库的结构都不同，但是至少有一列是一致的，综合起来就是数据的全集
    //        > 优点: 业务清晰，利于维护
    //        > 缺点: TODO: 如果单表的数据量大，读写压力依然很大，性能没有本质的提升
    //               / 业务可能会影响到数据库的瓶颈
    //               / 部分业务无法关联，join需要通过java程序去调用 (如果在分库中使用冗余数据，则可能造成数据一致性问题)
    //    3.2 水平拆分: 将同一个表的数据进行"分块"保存到不同的数据库中，这些数据库中的表结构完全相同
    //        每个库的结构一致，数据不同，综合起来就是数据的全集
    //        水平拆分需要保证均衡性
    //        > 优点: TODO: 单库可以保证数据的降低，数据被平分
    //               / 提高了负载能力，增加了高可用
    //               / 数据表的结构一致，程序适配性高 ==> 适用于项目进行到一定程度
    //        > 缺点: 数据的扩容有难度，维护量大 ==> 不方便hash均衡，负载均衡
    //               / 拆分规则很难抽象出来
    //               / 部分业务无法关联，join需要通过java程序去调用 (库中放置的数据的是一类的数据)
    // 4. 分库分表造成的问题
    //    4.1 分布式事务: 操作分布式的数据库，执行操作 ==> acid 无法保证跨库的事务
    //    4.2 跨库的join查询
    //    4.3 分布式自增全局唯一id(雪花算法，Redis)
    //    4.4 增加开发成本
    //    TODO：解决方案，开源框架
    //    jdbc直连层: ShardingSphere, tddl(ali)        ==> 性能更高，必须使用java语言，支持跨数据库
    //    proxy代理层: mycat(c语言),mysql-proxy(官方开源) ==> 需要网络通讯，跨进程，跨语言
}