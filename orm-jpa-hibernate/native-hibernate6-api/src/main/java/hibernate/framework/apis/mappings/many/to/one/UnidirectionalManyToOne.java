package hibernate.framework.apis.mappings.many.to.one;

import hibernate.framework.apis.mappings.many.to.one.model.Grade;
import hibernate.framework.apis.mappings.many.to.one.model.Student;
import hibernate.framework.apis.mappings.many.to.one.model.StudentAddress;
import hibernate.framework.apis.session.HibernateSessionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

// TODO. 完全根据对象生成数据库中的表
// 建议先存储外键的One一端在Many端，避免额外执行update操作影响性能
// 如果设置的外键属性not-null，则必须先存储外键的One一端
public class UnidirectionalManyToOne {

    public static void main(String[] args) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateSessionUtil.getSession();
            transaction = session.beginTransaction();

            // 先存储One-端的数据，Many-端外键指向的数据
            Grade grade1 = new Grade("basic 1");
            Grade grade2 = new Grade("master 1");
            session.persist(grade1);
            session.persist(grade2);

            hibernate.framework.apis.mappings.many.to.one.package1.Grade grade11 =
                    new hibernate.framework.apis.mappings.many.to.one.package1.Grade("basic 11");
            hibernate.framework.apis.mappings.many.to.one.package1.Grade grade22 =
                    new hibernate.framework.apis.mappings.many.to.one.package1.Grade("master 11");
            session.persist(grade11);
            session.persist(grade22);

            StudentAddress address1 = new StudentAddress("my home 1");
            StudentAddress address2 = new StudentAddress("my home 2");
            session.persist(address1);
            session.persist(address2);

            // 再存储Many-端的对象，设置对象属性的值
            Student student1 = new Student("student name 11", 15, grade1);
            student1.setGrade1(grade11);
            student1.setAddress(address1);
            Student student2 = new Student("student name 22", 25, grade1);
            student2.setGrade1(grade22);
            student2.setAddress(address1);
            Student student3 = new Student("student name 33", 35, grade2);
            student3.setGrade1(grade22);
            student3.setAddress(address2);

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
}
