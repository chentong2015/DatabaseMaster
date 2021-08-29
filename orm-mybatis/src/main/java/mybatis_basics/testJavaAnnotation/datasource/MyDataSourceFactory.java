package mybatis_basics.testJavaAnnotation.datasource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class MyDataSourceFactory implements DataSourceFactory {

    private Properties properties;

    // 设置从PropertiesLoader加载的properties配置信息
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    // 使用properties配置信息生成连接数据库的dataSource
    @Override
    public DataSource getDataSource() {
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(properties.getProperty("driverClassName"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        return dataSource;
    }
}
