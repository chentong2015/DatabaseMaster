package JdbcTemplateJdcp;

import JdbcTemplateJdcp.base.Information;
import JdbcTemplateJdcp.base.InformationDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Spring framework provides template APIs
// 1. org.springframework.jdbc.core.JdbcTemplate 该JdbcTemplate构建在JDBC之上，提供数据库的连接和相关操作
// 2. org.springframework.jdbc.core.nameparam.NamedParameterJdbcTemplate JdbcTemplate的优化，提供query的参数化
// 3. org.springframework.jdbc.core.SimpleJdbcTemplate
public class JdbcTemplateJdcp {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // 通过注入的bean的id来获取
        InformationDao informationDao = (InformationDao) context.getBean("baseJdbcTemplate");
        Information information = informationDao.getInformation(2);
        System.out.println(information);

        // 需要先注入Bean，再通过注入的类型来获取
        // InformationDao informationDao = context.getBean(BaseNamedParameterJdbcTemplate.class);

        // TODO: JdbcTemplate并不会抛出任何checked异常(JDBC SQL Exceptions), 通常需要自定义检测
        // try {
        //     information = informationDao.getInformation(10); // 可能报错的查询
        //     System.out.println(information);
        // } catch (BadSqlGrammarException e) {
        //     System.out.println("bad sql exception");
        // } catch (DataAccessException exception) {
        //     System.out.println("Exception data access");
        // }
        context.close();
    }
}
