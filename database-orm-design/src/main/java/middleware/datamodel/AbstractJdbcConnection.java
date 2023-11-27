package middleware.datamodel;

// TODO. 设计一个JDBC Connection抽象类型，抽象方法应该由具体的数据库RealConnection来实现
public abstract class AbstractJdbcConnection {

    /**
     * 返回AutoCommit Mode模式是false, 忽略了和底层数据库的相关性
     */
    public boolean getAutoCommitMode() {
        return !supportsDDLInTransaction();
    }

    /**
     * Determines if the database supports DDL within a transaction or not.
     *
     * @return True if the database supports DDL within a transaction, otherwise false.
     */
    // TODO this might be a dangerous default value.
    //  I would rather make this an abstract method and have every implementation specify it explicitly.
    public boolean supportsDDLInTransaction() {
        return true;
    }

}
