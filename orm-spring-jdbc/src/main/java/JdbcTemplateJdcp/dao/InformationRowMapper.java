package JdbcTemplateJdcp.dao;

import JdbcTemplateJdcp.base.Information;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Spring Interface RowMapper: mapping rows of a ResultSet by JdbcTemplate
 * 实现了这个接口的类型执行map each row to a result object的实际操作
 */
public class InformationRowMapper implements RowMapper<Information> {

    /**
     * RowMapper 将DB中的row map映射到指定object对象
     * 1. resultSet: the sets of rows by query SQL
     * 2. rowNum: the number of rows returned
     */
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
