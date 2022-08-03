package orm.middleware.design.api;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionValidator {

    ConnectionValidator ALWAYS_VALID = connection -> true;

    boolean isValid(Connection connection) throws SQLException;
}
