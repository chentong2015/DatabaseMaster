package com.liquibase.main.datatype;

import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.TimestampType;

// TODO. 根据不同的database来映射到不同的类型
// liquibase底层支持非常多数据库的交互, 需要DB间的适配
public class MyDateTimeType extends TimestampType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (database instanceof SybaseDatabase) {
            return new DatabaseDataType("timestamp");
        }
        if (database instanceof OracleDatabase) {
            return new DatabaseDataType("TIMESTAMP", getParameters());
        }
        if (database instanceof MSSQLDatabase) {
            return new DatabaseDataType("rowversion");
        }
        return super.toDatabaseDataType(database);
    }
}
