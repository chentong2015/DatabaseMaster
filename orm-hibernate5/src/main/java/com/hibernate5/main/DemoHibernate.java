package com.hibernate5.main;

import com.hibernate5.main.entity.Book;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class DemoHibernate {

    public static void main(String[] args) {
        System.out.println("start...");
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        Query<Book> query = session.createQuery("from Book", Book.class);
        List<Book> books = query.getResultList();
        for (Book book : books) {
            System.out.println(book.getId() + " - " + book.getName() + " - " + book.getTitle());
        }
        System.out.println("end ");
        session.close();
    }
}
