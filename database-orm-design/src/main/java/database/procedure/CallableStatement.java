package database.procedure;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class CallableStatement {

    // 通过connection.prepareCall()调用DB中预先定义好的Procedure Function
    // 1. 定义传递的参数
    // 2. 定义返回值的类型
    // Creates a CallableStatement object for calling database stored procedures.
    // The CallableStatement object provides methods for setting up its IN and OUT parameters,
    // and methods for executing the call to a stored procedure.
    public long testCallableStatement(Connection connection, String field1) {
        try (java.sql.CallableStatement statement = connection.prepareCall("{call sp_getUniqueId(?, ?, ?, ?)}")) {
            statement.setString(1, field1);
            statement.registerOutParameter(2, Types.BIGINT);
            statement.execute();
            return statement.getLong(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
