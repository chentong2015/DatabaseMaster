package JdbcTemplateJdcp.dao;

import JdbcTemplateJdcp.base.Information;
import JdbcTemplateJdcp.base.InformationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * NamedParameterJdbcTemplate: Template class with a basic set of JDBC operations   ===> JdbcTemplate的优化 !!
 * 1. Allowing the use of named parameters rather than traditional '?' placeholders
 * 2. 使用具有名称的参数替代传统的'?' placeholders, 解决代码可读性的问题
 * 3. 解决参数的顺序问题
 */
@Repository
public class BaseNamedParameterJdbcTemplate implements InformationDao {

    private NamedParameterJdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * BeanPropertySqlParameterSource: implementation of SqlParameterSource
     * 1. Obtains parameter values from bean properties of a given JavaBean object
     * 2. The names of the bean properties have to match the parameter names.
     * 从指定的Java bean object对象中获取指定数目值(对象中的属性名称和参数的名称必须严格一致) !!!
     */
    @Override
    public boolean insertInformation(Information info) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(info);
        String sqlQuery = "INSERT INTO information (id, name, place, year) VALUES (:id, :name, :place, :year)";
        return template.update(sqlQuery, beanParams) == 1;
    }

    /**
     * MapSqlParameterSource: SqlParameterSource implementation that holds a given Map of parameters.
     * 使用Map格式的参数
     */
    @Override
    public Information getInformation(int id) {
        SqlParameterSource params = new MapSqlParameterSource("ID", id);
        String sqlQuery = "SELECT * FROM information where id = :ID";
        return template.queryForObject(sqlQuery, params, new InfoRowMapper());
    }

    @Override
    public void cleanupTable() {
        String sqlQuery = "TRUNCATE TABLE information";
        template.getJdbcOperations().execute(sqlQuery);
    }
}
