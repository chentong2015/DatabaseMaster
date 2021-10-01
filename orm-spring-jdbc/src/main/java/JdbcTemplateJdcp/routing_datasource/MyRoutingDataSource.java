package JdbcTemplateJdcp.routing_datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

// Spring使用路由来切换多个数据源
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
