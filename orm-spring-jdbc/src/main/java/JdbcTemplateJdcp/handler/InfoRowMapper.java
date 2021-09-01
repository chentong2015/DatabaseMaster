package JdbcTemplateJdcp.handler;

import JdbcTemplateJdcp.base.Information;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// TODO: 获取结果时，需要提供Table Row和Java Object之间的映射  ==> ORM层提供的实现 !!
// 自定义实现RowMapper<T>接口，mapping rows of a ResultSet by JdbcTemplate
public class InfoRowMapper implements RowMapper<Information> {

    // 1. resultSet: the sets of rows by query SQL
    // 2. rowNum: the number of rows returned
    @Override
    public Information mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Information info = new Information();
        info.setId(resultSet.getInt("id"));
        info.setName(resultSet.getString("name"));
        info.setPlace(resultSet.getString("place"));
        info.setYear(resultSet.getInt("year"));
        return info;
    }
}
