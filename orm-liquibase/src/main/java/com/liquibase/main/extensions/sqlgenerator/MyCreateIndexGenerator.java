package com.liquibase.main.extensions.sqlgenerator;

import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateIndexGenerator;
import liquibase.statement.core.CreateIndexStatement;

public class MyCreateIndexGenerator extends CreateIndexGenerator {

    // TODO. 必须要设置权限优先级，才能覆盖Liquibase原始API
    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    // TODO. 对于SQL Server, Index索引的创建需要设置PAGE_LOCKS属性
    // 如果直接调用super method来修改返回的结果，由于UnparsedSql封装了SQL执行语句，导致无法补充修改
    @Override
    public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Sql[] generatedSql = super.generateSql(statement, database, sqlGeneratorChain);

        String sqlValue = "null";
        if (database instanceof MSSQLDatabase) {
            sqlValue = generatedSql[0].toSql().concat(" with (ALLOW_PAGE_LOCKS = OFF)");
        }
        System.out.println(sqlValue);
        return generatedSql;
    }
}
