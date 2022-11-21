package com.liquibase.main.extensions.sqlgenerator;

import liquibase.change.AddColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.*;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateIndexGenerator;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.util.StringUtil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MyCreateIndexGenerator extends CreateIndexGenerator {

    @Override
    public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        List associatedWith;
        if (database instanceof OracleDatabase) {
            associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",");
            if (associatedWith != null && (associatedWith.contains("primaryKey") || associatedWith.contains("uniqueConstraint"))) {
                return new Sql[0];
            }
        } else {
            associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",");
            if (associatedWith != null && (associatedWith.contains("primaryKey") || associatedWith.contains("uniqueConstraint") || associatedWith.contains("foreignKey"))) {
                return new Sql[0];
            }
        }

        StringBuilder buffer = new StringBuilder();
        buffer.append("CREATE ");
        if (statement.isUnique() != null && statement.isUnique()) {
            buffer.append("UNIQUE ");
        }
        if (database instanceof MSSQLDatabase && statement.isClustered() != null) {
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
        if (database instanceof OracleDatabase && statement.isClustered() != null && Boolean.TRUE.equals(statement.isClustered())) {
            buffer.append("CLUSTER ");
        }

        buffer.append(database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName())).append("(");
        Iterator<AddColumnConfig> iterator = Arrays.asList(statement.getColumns()).iterator();
        while (iterator.hasNext()) {
            AddColumnConfig column = iterator.next();
            // How to test this line ?
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
            if (!(database instanceof MSSQLDatabase) && !(database instanceof SybaseASADatabase)) {
                if (!(database instanceof AbstractDb2Database) && !(database instanceof InformixDatabase)) {
                    buffer.append(" TABLESPACE ").append(statement.getTablespace());
                } else {
                    buffer.append(" IN ").append(statement.getTablespace());
                }
            } else {
                buffer.append(" ON ").append(statement.getTablespace());
            }
        }
        if (database instanceof AbstractDb2Database && statement.isClustered() != null && Boolean.TRUE.equals(statement.isClustered())) {
            buffer.append(" CLUSTER");
        }

        // TODO. 对于SQL Server, Index索引的创建需要设置PAGE_LOCKS属性
        if (database instanceof MSSQLDatabase) {
            buffer.append(" with (ALLOW_PAGE_LOCKS = OFF)");
        }

        return new Sql[]{new UnparsedSql(buffer.toString(), this.getAffectedIndex(statement))};
    }
}
