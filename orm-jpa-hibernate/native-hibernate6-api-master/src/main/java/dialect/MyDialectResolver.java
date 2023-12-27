package dialect;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.hibernate.dialect.Oracle9iDialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

// TODO. 根据Database数据库的不同，解析成不同的Dialect方言
// Hibernate没有为SQLite提供Dialect，需要自定义提供，否则无法建立连接
public class MyDialectResolver implements DialectResolver {

    @Override
    public Dialect resolveDialect(DialectResolutionInfo dialectResolutionInfo) {
        String databaseName = dialectResolutionInfo.getDatabaseName();
        if ("Oracle".equals(databaseName)) {
            // return new Oracle10Dialect();
        }
        if ("PostgreSQL".equals(databaseName)) {
            // return new PostgreSQL95Dialect();
        }

        // 下面通过database来解析的方式适用于5.6版本
        // for (Database database : Database.values()) {
        //     Dialect dialect = database.resolveDialect(info);
        //     if ( dialect != null ) {
        //         return dialect;
        //     }
        // }
        return null;
    }

    private Dialect getHibernateDialect(String databaseName, int majorVersion) {
        if ("Oracle".equals(databaseName)) {
            switch (majorVersion) {
                case 8:
                    return new Oracle8iDialect();
                case 9:
                    return new Oracle9iDialect();
                default:
                    return new Oracle10gDialect();
            }
        }
        return null;
    }
}
