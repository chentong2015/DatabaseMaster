package com.liquibase.main.extensions;

// 将自定义的类型注册到Factory, 提供Liquibase的自定义构建
public class LiquibaseHelper {

    public void registerDatabase() {
        // DatabaseFactory.getInstance().register(new MyDatabase());
        // DataTypeFactory.getInstance().register(new MyDateTimeType());
        // DatabaseFactory.getInstance().getDatabase("db name");

        // SnapshotGeneratorFactory.getInstance().register(new MyColumnSnapshotGenerator());
        // PreconditionFactory.getInstance().register(new MyIsNullablePrecondition());
        // SqlGeneratorFactory.getInstance().register(new MyDropColumnGenerator());

        // SqlGeneratorFactory.getInstance().register(new MyCreateIndexGenerator());
        // SqlGeneratorFactory.getInstance().register(new MyCreateIndexGenerator());
    }
}
