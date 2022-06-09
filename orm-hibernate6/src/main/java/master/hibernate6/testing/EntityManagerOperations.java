package master.hibernate6.testing;

import com.hibernate6.main.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

// EntityManager提供的公共API操作
public class EntityManagerOperations {

    private static final EntityManagerFactory emf;

    /**
     * Static block for creating EntityManagerFactory.
     * The Persistence class looks for META-INF/persistence.xml in the classpath.
     */
    static {
        String persistenceUnitName = "master.hibernate.testing.operations";
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    /**
     * Static method returning EntityManager.
     *
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        // 通过EntityManagerFactory来获取EntityManager
        return emf.createEntityManager();
    }

    /**
     * Saves the movie entity into the database.
     * Here we are using Application Managed EntityManager,
     * hence should handle transactions by ourselves.
     */
    public void saveBook() {
        EntityManager em = EntityManagerOperations.getEntityManager();
        em.getTransaction().begin();
        Book book = new Book();
        book.setName("java");
        book.setTitle("java title");
        em.persist(book);
        em.getTransaction().commit();
    }

    /**
     * Method to illustrate the querying support in EntityManager
     * when the result is a single object.
     */
    // TODO. 参数的占位符需要表明数字序号 !!
    public Book queryForBookById() {
        EntityManager em = EntityManagerOperations.getEntityManager();
        return (Book) em.createQuery("SELECT book from Book book where book.id = ?1")
                .setParameter(1, 1L)
                .getSingleResult();
    }

    /**
     * Method to illustrate the querying support in EntityManager
     * when the result is a list.
     */
    public List<Book> queryForBooks() {
        EntityManager em = EntityManagerOperations.getEntityManager();
        return em.createQuery("SELECT book from Book book where book.name = ?1")
                .setParameter(1, "java")
                .getResultList();
    }

    /**
     * Method to illustrate the usage of find() method.
     */
    // TODO. 根据指定的class类型就能获取到对应的table表中的数据
    public Book getBook(Long bookId) {
        EntityManager em = EntityManagerOperations.getEntityManager();
        return em.find(Book.class, bookId);
    }

    /**
     * Method to illustrate the usage of merge() function.
     */
    // 从persistence context持久层上下文中移除和merge更新
    public void mergeBook() {
        EntityManager em = EntityManagerOperations.getEntityManager();
        Book book = getBook(1L);
        em.detach(book);
        book.setTitle("new titel");
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
    }

    /**
     * Method to illustrate the usage of remove() function.
     */
    public void removeBook() {
        EntityManager em = EntityManagerOperations.getEntityManager();
        em.getTransaction().begin();
        Book book = getBook(1L);
        em.remove(book);
        em.getTransaction().commit();
    }
}
