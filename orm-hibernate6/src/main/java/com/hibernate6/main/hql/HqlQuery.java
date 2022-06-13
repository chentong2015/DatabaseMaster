package com.hibernate6.main.hql;

import com.hibernate6.main.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

// TODO. HQL: hibernate query language, 一种类似于sql的简化的查询语言, 不是SQL语言
// "Book": @Entity标记的name名称，或者使用类型作为名称 !!
// "form Book": 提取Book表的所有的信息，并映射成指定类型的对象
public class HqlQuery {

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
