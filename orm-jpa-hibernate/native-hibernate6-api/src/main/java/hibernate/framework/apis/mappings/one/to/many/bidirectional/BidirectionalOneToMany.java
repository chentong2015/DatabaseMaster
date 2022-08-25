package hibernate.framework.apis.mappings.one.to.many.bidirectional;

import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Set;

// TODO. 可以通过两端两个方向来获取数据，存储的方式两种均可
public class BidirectionalOneToMany {

    private static void testSaveData() {
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
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }

    private static void testGetDataFromManySide() {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSession();
            Student student = session.get(Student.class, 3);
            Grade grade = student.getGrade();
            System.out.println("Student: " + student.getName() + ", " + student.getAge());
            System.out.println(grade.getName());
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }

    private static void testGetDataFromOneSide() {
        Session session = null;
        try {
            session = HibernateSessionUtil.getSession();
            Grade grade = session.get(Grade.class, 1);
            System.out.println(grade.getName());

            Set<Student> students = grade.getStudents();
            for (Student student : students) {
                System.out.println("Student: " + student.getName() + ", " + student.getAge());
            }
        } finally {
            HibernateSessionUtil.closeSession();
        }
    }
}
