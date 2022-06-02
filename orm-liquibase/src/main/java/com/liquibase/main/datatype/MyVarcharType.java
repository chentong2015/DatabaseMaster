package com.liquibase.main.datatype;

import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.VarcharType;

// Varchar类型的转换
@DataTypeInfo(name = "varchar",
        aliases = {"java.sql.Types.VARCHAR", "java.lang.String", "varchar2",
                "character varying", "string", "UROWID"
        }, minParameters = 0, maxParameters = 1, priority = 5
)
public class MyVarcharType extends VarcharType {

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if ((database instanceof MSSQLDatabase)
                || (database instanceof SybaseDatabase)
                || (database instanceof PostgresDatabase)) {
            if ((this.getParameters().length > 0) && ((String) this.getParameters()[0]).contains(" CHAR")) {
                String firstParameter = ((String) this.getParameters()[0]);
                String parameter = firstParameter.replace(" CHAR", "");
                return new DatabaseDataType("VARCHAR", parameter);
            }
            return new DatabaseDataType("VARCHAR", this.getParameters());
        }
        return super.toDatabaseDataType(database);
    }
}
