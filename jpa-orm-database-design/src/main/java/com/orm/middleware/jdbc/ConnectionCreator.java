package com.orm.middleware.jdbc;

import java.sql.Connection;

interface ConnectionCreator {

    String getUrl();
	
    Connection createConnection();
}
