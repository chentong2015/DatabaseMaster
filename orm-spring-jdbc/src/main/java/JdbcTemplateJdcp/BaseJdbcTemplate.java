package JdbcTemplateJdcp;

import JdbcTemplateJdcp.base.Information;
import JdbcTemplateJdcp.base.InformationDao;
import JdbcTemplateJdcp.dao.BaseNamedParameterJdbcTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;

/**
 * JdbcTemplate    : 连接数据库和执行SQL的机制, 使用JDBC API, 更容易控制异常
 * Connection Pool : 连接池, 连接数据库的cache暂存, 以便在之后的请求重新使用该connection
 * CRUD 增删改查    : create, update, read, delete
 * ------------
 * Spring framework provides template APIs 使用模板API可以避免直接写connections, closing, 等代码
 * 1. org.springframework.jdbc.core.JdbcTemplate
 * 2. org.springframework.jdbc.core.nameparam.NamedParameterJdbcTemplate
 * 3. org.springframework.jdbc.core.SimpleJdbcTemplate
 */
public class BaseJdbcTemplate {

    /**
     * DAO(Data Access Object) Design Pattern    ==> Repository 仓库/资源库
     * 1. 将应用的持久层的细节(details persistence)抽象出来
     * 2. underlying persistence 数据库系统 <- JDBC ->  dao layer <- Objects -> service 业务层 -> Controller
     */

    /**
     * DTO(Data transfer object) Design Pattern   ==> Data Model 一种数据结构
     * 1. object used to encapsulate data, and send it from one sub system of config to anther
     * 2. 数据封装的对象，用于在应用和系统之间进行传递：特别是数据库通信, 解析套接字传递的信息等场景 ...
     */

    /**
     * Active Record: 一种特殊的DTO形式, 同时具有公共变量的数据结构和(对象才应该具有的)行为方法 !!
     */

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // InformationDao informationDao = (InformationDao) context.getBean("infoDao");
        InformationDao informationDao = context.getBean(BaseNamedParameterJdbcTemplate.class);
        Information information = informationDao.getInformation(2);
        System.out.println(information);
        context.close();
    }

    /**
     * TODO: Spring JDBC App don't throw any checked(无法忽略) normal JDBC SQL Exceptions
     * It throws unchecked data access exceptions or a subclass of that exception
     */

    /**
     * Class DataAccessException: 可以使用try语句块捕获异常，但是应该在persistence layer之外 !!
     * - java.lang.Exception
     * -- java.lang.RuntimeException
     * --- org.springframework.dao.DatClassException  ===> 继承自RuntimeException的为unchecked exception
     */
    public static void main111(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        InformationDao informationDao = (InformationDao) context.getBean("infoDao");
        Information information = null;
        try {
            information = informationDao.getInformation(10); // 可能报错的查询
            System.out.println(information);
        } catch (BadSqlGrammarException e) {
            System.out.println("bad sql exception");
        } catch (DataAccessException exception) {
            System.out.println("Exception data access");
        }
        context.close();
    }
}
