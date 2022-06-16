package hibernate.framework.apis.mapping.unidirectional.one.to.many;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

// 先存储One端的数据，再存储many端的数据
// 存储是一定会存在update更新操作，效率不如Many-To-One
public class UndirectionalOneToMany {

    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            Student student1 = new Student("name1", 10);
            Student student2 = new Student("name2", 20);
            Student student3 = new Student("name3", 30);
            Grade grade1 = new Grade("basic");
            grade1.getStudents().add(student1);
            grade1.getStudents().add(student2);
            Grade grade2 = new Grade("master");
            grade2.getStudents().add(student3);

            session.persist(grade1);
            session.persist(grade2);
            session.persist(student1);
            session.persist(student2);
            session.persist(student3);
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            transaction.rollback();
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }
}
