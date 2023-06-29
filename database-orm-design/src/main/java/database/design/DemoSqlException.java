package database.design;

// SQLException封装了database access可能抛出的异常
public class DemoSqlException {

    // 1. Connection String连接字符串出错, 无法建立起安全的连接
    //   Caused by: com.microsoft.sqlserver.jdbc.SQLServerException:
    //   The driver could not establish a secure connection to SQL Server by using Secure Sockets Layer (SSL) encryption.
    //   Error: "PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
    //   unable to find valid certification path to requested target". ClientConnectionId:3f38188f-f9e1-48a2-b5b7-e688f69f7663
    //
    //   at com.microsoft.sqlserver.jdbc.SQLServerConnection.terminate(SQLServerConnection.java:3806)
    //   at com.microsoft.sqlserver.jdbc.TDSChannel.enableSSL(IOBuffer.java:1906)
    //   at com.microsoft.sqlserver.jdbc.SQLServerConnection.connectHelper(SQLServerConnection.java:3329)
    //   at com.microsoft.sqlserver.jdbc.SQLServerConnection.login(SQLServerConnection.java:2950)
    //   at com.microsoft.sqlserver.jdbc.SQLServerConnection.connectInternal(SQLServerConnection.java:2790)
    //   at com.microsoft.sqlserver.jdbc.SQLServerConnection.connect(SQLServerConnection.java:1663)
    //   at com.microsoft.sqlserver.jdbc.SQLServerDriver.connect(SQLServerDriver.java:1064)
    //   ... 24 more
}
