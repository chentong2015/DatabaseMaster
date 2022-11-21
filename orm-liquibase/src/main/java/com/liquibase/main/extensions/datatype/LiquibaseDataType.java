package com.liquibase.main.extensions.datatype;

import liquibase.change.core.LoadDataChange;

public class LiquibaseDataType extends liquibase.datatype.LiquibaseDataType {

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }
}
