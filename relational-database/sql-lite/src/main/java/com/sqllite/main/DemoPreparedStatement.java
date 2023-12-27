package com.sqllite.main;

import java.sql.*;

// PreparedStatement能够解决使用SQL Injection黑掉数据库的方式，但是无法避免别的数据库attack
public class DemoPreparedStatement {

    private Connection connection;
    // 创建instance variable, 避免由于每次query的时候编译对性能造成的影响
    private PreparedStatement preparedStatement;

    /**
     * 针对需要根据条件筛选的查询语句: ? placeholder character
     * 1. PreparedStatement将?符号所替换的信息处理成字面值，而非解释成带sql语句的字符串(当字符串中含有sql关键字时) !!
     * 2. ? 不能替换table和column的名称，PreparedStatement需要在预编译的时候知道这些信息
     */
    private void testSQLInjection(String searchName) {
        // SQL Injection通过在SQL筛选条件中注入or关键字判断条件，使得查询语句能够检索出table中的所有信息
        // 使得WHERE判断条件失效，返回了非相关用户的信息，造成信息泄露
        String queryNotOK = "SELECT * FROM users_view WHERE name=\"chen\" or 1=1 or \"\"";

        String queryOK = "SELECT * FROM users_view WHERE name=?"; //
        String queryCheck = "SELECT * FROM users_view WHERE name=\"chen or 1=1 or \""; // 解析后的SQL查询语句
        try {
            connection = DriverManager.getConnection("url");
            preparedStatement = connection.prepareStatement("select * from users_view");
            preparedStatement.setString(1, searchName); // index对应?标识符的位置
            ResultSet results = preparedStatement.executeQuery();

            preparedStatement.close();
            results.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * preparedStatement.executeUpdate()
     * 1. 指定执行数据操作语句, 或者没有数据返回的查询statement !!
     * 2. 返回值是受影响的row行数
     * Executes the SQL statement in this PreparedStatement object for SQL Data Manipulation Language (DML) statement
     * such as INSERT, UPDATE or DELETE; or an SQL statement that returns nothing, such as a DDL statement.
     */
    private void testExecuteUpdate(int id, String newName) {
        String query = "UPDATE users SET name = ? WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, id);
            int affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
