package com.sqllite.main.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FACADE模式
 * 1. 为一组具有复杂且全面的接口的对象提供一个简单且特定的接口
 * 2. Datasource隐藏java.sql的复杂性，关于数据库的操作都是透过Datasource来实现
 * 大数据库的设计模式：
 * 1. 数据库的连接Connection来自Connection pool
 * 2. 针对每一个table创建一个model class
 * 3. 封装在数据库模型类型中的方法，不应该对外暴露实现的细节，比如：
 */
public class Datasource {

    private Connection connection;
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    // Model Table 设计(封装)
    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ID = "_id";  // _id 是为了方便java程序调使用
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ARTIST = "artist";
    public static final int INDEX_ID = 1;
    public static final int INDEX_NAME = 2;
    public static final int INDEX_ARTIST = 3;

    public boolean connect() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        } catch (SQLException exception) {
            System.out.println("cannot connect to database");
            return false;
        }
    }

    // 优化复杂查询的硬编码：提取SQL的核心语句，构建const字符串，根据不同的查询替换指定的字符串
    // 可以使用StringBuilder来构建查询的字符串
    public void queryAlbumsForArtist(String artistName, int sortOder) {
        String query = "SELECT albums.name FROM albums \n" +
                "INNER JOIN artists ON albums.artist = artists._id \n" +
                "WHERE artists.name = " + artistName + " \n" +
                "ORDER BY albums.name \n" +
                "COLLATE NOCASE ASC";
        System.out.println(query); // 测试查询的SQL
    }

    // 不直接返回查询的结果ResultSet给调用者(不应该知道细节), 而是返回经过处理之后的数据, 对table进行建模model
    public List<Album> getAlbums() {
        String query = "SELECT * FROM albums";
        try (Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(query)) {
            return parseAlbumResults(results);
        } catch (SQLException ex) {
            System.out.println("Query error" + ex.getMessage());
        }
        return new ArrayList<>(); // 不要返回null空值
    }

    /**
     * 使用columnLabel和columnIndex可能会造成性能的区别 ==> 使用label的可读性更强 !!
     * 1. 可以使用columnIndex来取值：列的位置变动或者插入新的列，代码会受到影响 !!
     * 2. columnIndex指的是查询出来的结果中的index，不是原表的index位置 !!
     * 3. columnIndex从1开始
     */
    private List<Album> parseAlbumResults(ResultSet results) throws SQLException {
        List<Album> albums = new ArrayList<>();
        while (results.next()) {
            Album album = new Album();
            album.setId(results.getInt("_id"));
            album.setName(results.getString("name"));
            album.setArtistId(results.getInt("artist"));
            albums.add(album);
        }
        return albums;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.out.println("Error closing connection");
        }
    }
}
