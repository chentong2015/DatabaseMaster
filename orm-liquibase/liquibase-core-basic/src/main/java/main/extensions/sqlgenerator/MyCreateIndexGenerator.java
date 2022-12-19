package main.extensions.sqlgenerator;

import liquibase.change.AddColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateIndexGenerator;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.util.StringUtil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

// 索引生成器中会根据不同DB来对应生成适配的Index
public class MyCreateIndexGenerator extends CreateIndexGenerator {

    // TODO. 必须要设置权限优先级，才能覆盖Liquibase原始API
    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    // 1. 如果直接调用super method来修改返回的结果，由于UnparsedSql封装了SQL执行语句，导致无法补充修改
    public Sql[] generateSqlVersion1(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Sql[] generatedSql = super.generateSql(statement, database, sqlGeneratorChain);

        String sqlValue = "null";
        if (database instanceof MSSQLDatabase) {
            sqlValue = generatedSql[0].toSql().concat(" with (ALLOW_PAGE_LOCKS = OFF)");
        }
        System.out.println(sqlValue);
        return generatedSql;
    }

    // 2. 使用Java Streams创建新UnparsedSql对象，强制改写super method返回的值
    //    使用java Stream流操作会降低可读性 !!
    public Sql[] generateSqlVersion2(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Sql[] generatedSql = super.generateSql(statement, database, sqlGeneratorChain);
        if (database instanceof MSSQLDatabase) {
            String mssql = generatedSql[0].toSql() + " with (ALLOW_PAGE_LOCKS = OFF)";
            return new Sql[]{new UnparsedSql(mssql, this.getAffectedIndex(statement))};
        }
        return generatedSql;
    }

    // 3. 使用特殊条件的反面，重用super method的源码逻辑，只抽象一种DB的逻辑
    public Sql[] generateSqlVersion3(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        if (!(database instanceof MSSQLDatabase)) {
            return super.generateSql(statement, database, sqlGeneratorChain);
        }
        List<String> associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",");
        if (associatedWith != null && (associatedWith.contains("primaryKey") || associatedWith.contains("uniqueConstraint") || associatedWith.contains("foreignKey"))) {
            return new Sql[0];
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("CREATE ");
        if (statement.isUnique() != null && statement.isUnique()) {
            buffer.append("UNIQUE ");
        }
        if (statement.isClustered() != null) {
            if (Boolean.TRUE.equals(statement.isClustered())) {
                buffer.append("CLUSTERED ");
            } else {
                buffer.append("NONCLUSTERED ");
            }
        }

        buffer.append("INDEX ");
        if (statement.getIndexName() != null) {
            String indexSchema = statement.getTableSchemaName();
            buffer.append(database.escapeIndexName(statement.getTableCatalogName(), indexSchema, statement.getIndexName())).append(" ");
        }
        buffer.append("ON ");
        buffer.append(database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName())).append("(");

        Iterator<AddColumnConfig> iterator = Arrays.asList(statement.getColumns()).iterator();
        while (iterator.hasNext()) {
            AddColumnConfig column = iterator.next();
            if (column.getComputed() == null) {
                buffer.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column.getName()));
            } else if (Boolean.TRUE.equals(column.getComputed())) {
                buffer.append(column.getName());
            } else {
                buffer.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column.getName()));
            }
            if (column.getDescending() != null && column.getDescending()) {
                buffer.append(" DESC");
            }
            if (iterator.hasNext()) {
                buffer.append(", ");
            }
        }
        buffer.append(")");
        if (StringUtil.trimToNull(statement.getTablespace()) != null && database.supportsTablespaces()) {
            buffer.append(" ON ").append(statement.getTablespace());
        }
        buffer.append(" with (ALLOW_PAGE_LOCKS = OFF)");
        return new Sql[]{new UnparsedSql(buffer.toString(), this.getAffectedIndex(statement))};
    }

    // 4. 复制super class方法源代码，重新改写方法逻辑
    public Sql[] generateSqlVersion4(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        // copy source code from super method
        // update source code
        return new Sql[]{new UnparsedSql("")};
    }

    // TODO. 最佳选择：如果不能修改创建的对象，则重新创建对象
    @Override
    public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Sql[] generatedSql = super.generateSql(statement, database, sqlGeneratorChain);
        if (database instanceof MSSQLDatabase) {
            String mssql = generatedSql[0].toSql() + " with (ALLOW_PAGE_LOCKS = OFF)";
            return new Sql[]{new UnparsedSql(mssql, this.getAffectedIndex(statement))};
        }
        return generatedSql;
    }
}





















