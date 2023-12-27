package main.config;

public class DatabaseConfig {

    private String url;
    private String user;
    private String password;

    public DatabaseConfig(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
}
