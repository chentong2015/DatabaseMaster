package naming.strategies;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

// 2. 自定映射名称的策略
// 数据库的table名称在不同的数据库中会被自动转换成不同的格式
// PostgresSQL: HEDGE#STRATEGY_DBF -> HEDGE$STRATEGY_DBF
public class MyPhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        if (jdbcEnvironment.getDialect() instanceof PostgresPlusDialect) {
            String newTableName = identifier.toString().replace('#', '$');
            return Identifier.toIdentifier(newTableName);
        }
        return null;
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return null;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return null;
    }
}
