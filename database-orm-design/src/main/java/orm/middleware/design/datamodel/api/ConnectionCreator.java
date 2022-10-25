package orm.middleware.design.datamodel.api;

import java.sql.Connection;

public interface ConnectionCreator {

    String getUrl();

    Connection createConnection();
}
