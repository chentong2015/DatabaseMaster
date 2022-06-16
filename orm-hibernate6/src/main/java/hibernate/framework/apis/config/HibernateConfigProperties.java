package hibernate.framework.apis.config;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

// TODO. 使用java代码的方式提供hibernate配置信息，通过Configuration构建SessionFactory
public class HibernateConfigProperties {

    // AvailableSettings为hibernate中cfg文件中所支持的配置
    public void testHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.SHOW_SQL, "true");
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create");

        Configuration configuration = new Configuration()
                .addProperties(properties).configure();
        Configuration configuration2 = new Configuration()
                .addResource("hibernate.cfg.xml").configure();
    }
}
