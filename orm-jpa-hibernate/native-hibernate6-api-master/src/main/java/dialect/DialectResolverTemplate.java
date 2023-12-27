package dialect;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;

public class DialectResolverTemplate implements DialectResolver {

    private static final long serialVersionUID = 3438546111602228772L;
    private static final int ORACLE_8 = 8;
    private static final int ORACLE_9 = 9;
    private static final int SYBASE_11 = 11;
    private static final int SYBASE_12 = 12;

    // 可以自定义Dialect，解析到自定义的Dialect
    @Override
    public Dialect resolveDialect(DialectResolutionInfo info) {
        String databaseName = info.getDatabaseName();
        int databaseMajorVersion = info.getDatabaseMajorVersion();
        /*
        if ("Adaptive Server Enterprise".equals(databaseName)) {
            return switch (databaseMajorVersion) {
                case SYBASE_11 -> new Sybase11Dialect();
                case SYBASE_12 -> new Sybase12Dialect();
                default -> new Sybase15Dialect();
            };
        }
        if ("Oracle".equals(databaseName)) {
            return switch (databaseMajorVersion) {
                case ORACLE_8 -> new Oracle8Dialect();
                case ORACLE_9 -> new Oracle9Dialect();
                default -> new Oracle10Dialect();
            };
        }
        if (databaseName.startsWith("Microsoft SQL Server")) {
            return new SQLServer2012Dialect();
        }
        if ("PostgreSQL".equals(databaseName)) {
            return new MxPostgreSQL95Dialect();
        } */

        //Default Case
        final StandardDialectResolver standardDialectResolver = new StandardDialectResolver();
        return standardDialectResolver.resolveDialect(info);
    }
}
