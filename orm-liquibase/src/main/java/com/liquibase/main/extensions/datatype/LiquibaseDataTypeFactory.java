package com.liquibase.main.extensions.datatype;

import liquibase.datatype.DataTypeFactory;

public class LiquibaseDataTypeFactory {

    // 重写类型之后，需要完成注册
    public void testDataTypeFactory() {
        DataTypeFactory.getInstance().register(new MyDateTimeType());
        DataTypeFactory.getInstance().register(new MyVarcharType());
        // SqlGeneratorFactory.getInstance().register(new ModifyDataTypeGenerator());
        // PreconditionFactory.getInstance().register(new IndexExistsPrecondition());
        // SnapshotGeneratorFactory.getInstance().unregister(ColumnSnapshotGenerator.class);
        // SnapshotGeneratorFactory.getInstance().register(new ColumnSnapshotGenerator());
    }
}
