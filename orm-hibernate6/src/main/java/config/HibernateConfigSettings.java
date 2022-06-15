package config;

import org.hibernate.cfg.AvailableSettings;

import java.util.Properties;

// AvailableSettings为hibernate中cfg文件中所支持的配置
public class HibernateConfigSettings {

    protected final Properties properties;

    public HibernateConfigSettings() {
        properties = new Properties();
        properties.setProperty(AvailableSettings.SHOW_SQL, "true");
        properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "create");
    }
}
