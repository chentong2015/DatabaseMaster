package main.extensions.datatype;

import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.DateTimeType;

public class MyDateTimeType extends DateTimeType {

    // 将Liquibase Changelog中配置的Type类型映射到不同数据库的不同类型
    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof SybaseDatabase) {
            return new DatabaseDataType("timestamp");
        }
        if (database instanceof OracleDatabase) {
            return new DatabaseDataType("TIMESTAMP", getParameters());
        }
        if (database instanceof MSSQLDatabase) {
            return new DatabaseDataType("TIMESTAMP2", 3);
        }
        if (database instanceof PostgresDatabase) {
            return new DatabaseDataType("TIMESTAMP2", 3);
        }
        return super.toDatabaseDataType(database);
    }
}
