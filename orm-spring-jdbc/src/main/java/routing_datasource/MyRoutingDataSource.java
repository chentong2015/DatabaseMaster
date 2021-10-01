package routing_datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

// TODO: Spring使用路由来切换多个数据源
public class MyRoutingDataSource extends AbstractRoutingDataSource {

    // TODO: 数据表的获取(拼接)
    // MyBatis：
    // <insert id="insertOrder" parameterType="routing_datasource.dataModel.Order">
    //   使用拼接的方式获得最终的表: ${}是用来拼接的
    //   insert into order${tableSuffix} (order_id,user_id,money) values(#{orderId},#{userid},#{money})
    // </insert>

    // TODO: 数据库的获取
    // AbstractRoutingDataSource.determineTargetDataSource(){
    //    方法会推断使用的数据源，调用下面方法的实现
    //    Object lookupKey = this.determineCurrentLookupKey();
    //    拿到的lookupKey是动态获取的
    //    DataSource dataSource = (DataSource)this.resolvedDataSources.get(lookupKey);
    // }
    @Override
    protected Object determineCurrentLookupKey() {
        // 从Thread Local中拿到设置好的target data source信息(数据库的下标)
        return MultiDataSourceHolder.getDataSource();
    }

    // @Bean
    public void dataSource() {
        // 使用map来承载所有的数据源，方便做切换
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put("dataSource01", "dataSource01()");
        targetDataSource.put("dataSource02", "dataSource02()");
        targetDataSource.put("dataSource03", "dataSource03()");

        // 映射关系的mapping<求模之后的index，keyDataSource>
        Map<Integer, String> setMapping = new HashMap<>();
        setMapping.put(0, "dataSource01");
        setMapping.put(1, "dataSource02");
        setMapping.put(2, "dataSource03");
    }
}
