package split_db_table;

public class SplitDbTableStrategy {

    // 分库分表策略
    // 1. 多库多表 ROUTING_DS_TABLE_STRATEGY  --> RoutingDsAndTbStrategy
    // 2. 多库一表 ROUTING_DS_STRATEGY        --> RoutingDsStrategy
    // 3. 一库多表 ROUTING_TABLE_STRATEGY     --> RoutingTbStrategy
    
    // 规则类：接口类
    //    IRoutingDbTable {
    //       calDataSourceKey(String routingField) 计算数据落入到那个库中
    //       getTableKey(String routingField) 计算数据落入到库中的那个表中
    //       getFormatTableSuffix(Integer tableIndex) 表后缀的拼接的方式 order_info_001
    //       getRoutingFileHashCode(String routingField)
    //    }
    // 抽象类
    //    AbstractRoutingDbTable {
    //       公共方法统一提取到抽象类中，消除冗余代码
    //       dataSource.properties中静态数据配置检测
    //    }
    // 具体实现类

    // TODO: Spring使用路由来动态的切换DataSource数据源
    // 配置多个数据源，并从.properties文件中获取出来
    // @Bean
    // @ConfigurationProperties(prefix="spring.datasource.uid01")
    // public DataSource datasource01() {
    //    DataSource dataSource = new DataSource();
    //    dataSource.setUsername();
    //    ...
    //    return dataSource;
    // }
}
