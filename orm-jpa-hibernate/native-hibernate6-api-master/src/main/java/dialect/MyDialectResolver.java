package dialect;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

// TODO. 根据Database数据库的不同，解析成不同的Dialect方言
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
        return null;
    }
}
