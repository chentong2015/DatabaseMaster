package com.liquibase.main.datatype;

import liquibase.change.core.LoadDataChange;

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
public class LiquibaseDataType extends liquibase.datatype.LiquibaseDataType {

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }
}
