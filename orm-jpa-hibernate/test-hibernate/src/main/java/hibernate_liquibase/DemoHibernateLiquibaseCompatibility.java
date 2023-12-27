package hibernate_liquibase;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.id.enhanced.TableStructure;
import org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Liquibase 4.5.0 & Hibernate 5.6.14.Final 在使用上没有兼容性问题
public class DemoHibernateLiquibaseCompatibility {

    static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    static SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    static String changelogFilepath = "changelog-master.xml";
    private static String psqlConnectStr = "jdbc:postgresql://localhost:5432/my_database?user=postgres&password=admin";

    public static void main(String[] args) throws DatabaseException, SQLException {
        Connection connection = DriverManager.getConnection(psqlConnectStr);
        JdbcConnection jdbcConnection = new JdbcConnection(connection);

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        fireChangelog(database, changelogFilepath);

        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        // session.save(new Sample("sample1"));
        // session.save(new Sample("sample2"));
        session.getTransaction().commit();
        session.close();
        System.out.println("done.");
    }

    private static void fireChangelog(Database database, String changelogFilepath) {
        try (Liquibase liquibase = new Liquibase(changelogFilepath, new ClassLoaderResourceAccessor(), database)) {
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }

    public void test() {
        Session session = null;
        StandardServiceRegistryBuilder builder;
        TableStructure tableStructure;
        StandardServiceInitiator standardServiceInitiator;
        MutableIdentifierGeneratorFactory mutableIdentifierGeneratorFactory;
    }
}
