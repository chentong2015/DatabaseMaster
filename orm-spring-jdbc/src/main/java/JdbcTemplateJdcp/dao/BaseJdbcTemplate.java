package JdbcTemplateJdcp.dao;

import JdbcTemplateJdcp.base.Information;
import JdbcTemplateJdcp.base.InformationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @Repository: component annotation for the persistent or DB layer 特殊层(具有特殊功能的)component, 会被自动扫描
 */
@Repository("infoDao")
public class BaseJdbcTemplate implements InformationDao {

    // JdbcTemplate用来包装dataSource: 连接数据库和执行SQL的机制
    private JdbcTemplate jdbcTemplate;

    /**
     * dataSource:
     * 1. 注入在XML配置文件中声明的bean object(对于DataSource接口的具体实现)
     * 2. 本身是一个database connection factory工厂
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean insertInformation(Information info) {  // Information是一种DTO的数据类型
        // ? place holder 依次替换指定位置的参数
        String sqlQuery = "INSERT INTO information (id, name, place, year) VALUES (?, ?, ?, ?)";
        Object[] args = new Object[]{info.getId(), info.getName(), info.getPlace(), info.getYear()};
        return jdbcTemplate.update(sqlQuery, args) == 1; // Return the number of rows affected !!
    }

    @Override
    public Information getInformation(int id) {
        String sqlQuery = "SELECT * FROM information where id = ?";
        Object[] args = new Object[]{id};
        return jdbcTemplate.queryForObject(sqlQuery, new InfoRowMapper(), args);
    }

    // TRUNCATE 清空table中的所有信息，但是不删table ==> delete from table ==> DROP TABLE nom_table删除表
    @Override
    public void cleanupTable() {
        String sqlQuery = "TRUNCATE TABLE information";
        jdbcTemplate.execute(sqlQuery); // For DDL statement
    }
}
