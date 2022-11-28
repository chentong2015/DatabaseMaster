package com.hibernate.entity.manager;

import com.hibernate.entity.manager.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class DemoJakartaPersistenceAPI {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("base.jpa.demo");
        EntityManager entityManager = emf.createEntityManager();

        // 针对Update更新数据库的操作，需要开启事务
        entityManager.getTransaction().begin();
        Book book = new Book(4, "javax");
        entityManager.persist(book);
        entityManager.getTransaction().commit();

        // 执行原生的SQL查询语句
        String sqlString = "SELECT * FROM t_book";
        List<Book> books = entityManager.createNativeQuery(sqlString, Book.class).getResultList();
        for (Book b : books) {
            System.out.println(b);
        }
        entityManager.close();
        emf.close();
    }
}
