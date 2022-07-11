package demo;

import demo.entity.Employee;

import javax.persistence.EntityManager;

public class TestEntityManager {

    public static void test() {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp = entityManager.find(Employee.class, new Integer(1));
        System.out.println("Name:" + emp.getName() + ", City:" + emp.getCity());
        JPAUtilityHandler.close();
        System.out.println("Done");
    }

    public static void testContains() {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp1 = entityManager.find(Employee.class, 1);
        System.out.println(entityManager.contains(emp1));
        Employee emp2 = new Employee(2, "Suresh", "New Delhi");
        System.out.println(entityManager.contains(emp2));
        entityManager.close();
        JPAUtilityHandler.close();
    }

    public static void testDetach() {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp = entityManager.find(Employee.class, 1);
        System.out.println("Contains(before detach):" + entityManager.contains(emp));
        //Detach entity
        entityManager.detach(emp);
        System.out.println("Contains(After detach):" + entityManager.contains(emp));
        entityManager.close();
        JPAUtilityHandler.close();
    }

    public static void fetchEntity() {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        //fetch entity
        Employee emp = entityManager.find(Employee.class, new Integer(1));
        System.out.println("Name:" + emp.getName() + "," + "City: " + emp.getCity());
        System.out.println("Contains(before detach):" + entityManager.contains(emp));
        //detach entity
        entityManager.detach(emp);
        System.out.println("Contains(After detach):" + entityManager.contains(emp));
        //merge entity
        entityManager.getTransaction().begin();
        emp.setName("Brahma");
        emp.setCity("Kanpur");
        emp = entityManager.merge(emp);
        entityManager.getTransaction().commit();
        System.out.println("Contains(After merge):" + entityManager.contains(emp));
        System.out.println("Name:" + emp.getName() + "," + "City: " + emp.getCity());
        entityManager.close();
        JPAUtilityHandler.close();
    }

    public static void removeEntity() {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp = entityManager.find(Employee.class, new Integer(1));
        //start removing
        entityManager.getTransaction().begin();
        entityManager.remove(emp);
        entityManager.getTransaction().commit();
        entityManager.close();
        JPAUtilityHandler.close();
    }

    public static void updateEntity() {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp = entityManager.find(Employee.class, new Integer(1));
        System.out.println("Name:" + emp.getName() + ", City:" + emp.getCity());
        //start updating
        entityManager.getTransaction().begin();
        emp.setName("Krishna");
        emp.setCity("Allahabad");
        entityManager.getTransaction().commit();
        emp = entityManager.find(Employee.class, new Integer(1));
        entityManager.close();
        JPAUtilityHandler.close();
        System.out.println("Name:" + emp.getName() + ", City:" + emp.getCity());
    }

    public static void saveEntity() {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = new Employee(1, "Mahesh", "Varanasi");
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.close();
        JPAUtilityHandler.close();
    }
}
