package hibernate.framework.apis.query;

import hibernate.framework.apis.datamodel.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

// TODO. HQL: hibernate query language, 一种类似于sql的简化的查询语言
public class HqlQuery {

    // "Book": @Entity标记的name名称，默认是和Class的名称一致
    //  Use the java class name and property name of the mapped
    public static void testGetQuery(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            String fullNamePath = Book.class.getName();  // 全路径
            String entityNameByDefault = Book.class.getSimpleName(); // 只取类的名称
            String hqlQuery = "FROM " + entityNameByDefault;
            Query<Book> query = session.createQuery(hqlQuery, Book.class);
            List<Book> books = query.getResultList();
            for (Book book : books) {
                System.out.println(book.getId() + " - " + book.getName() + " - " + book.getTitle());
            }
        }
    }
}
