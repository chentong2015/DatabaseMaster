package com.liquibase.main.datatype;

import liquibase.change.core.LoadDataChange;
import liquibase.datatype.LiquibaseDataType;

// public static enum LOAD_DATA_TYPE {
//     BOOLEAN,
//     NUMERIC,
//     DATE,
//     STRING,
//     COMPUTED,
//     SEQUENCE,
//     BLOB,
//     CLOB,
//     SKIP,
//     UUID,
//     OTHER,
//     UNKNOWN;
//     private LOAD_DATA_TYPE() {}
// }
public class MyLiquibaseDataType extends LiquibaseDataType {

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }
}
