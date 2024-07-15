package demo2;

import java.sql.Connection;

public interface ConnectionCreator {

    String getUrl();

    Connection createConnection();
}
