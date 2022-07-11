package com.jpa.demo;

import com.jpa.demo.entity.Employee;

import javax.persistence.EntityManager;

public class TestEntityManager {

    public static void main0(String[] args) {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp = entityManager.find(Employee.class, new Integer(1));
        System.out.println("Name:" + emp.getName() + ", City:" + emp.getCity());
        JPAUtilityHandler.close();
        System.out.println("Done");
    }

    public static void main(String[] args) {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp1 = entityManager.find(Employee.class, 1);
        System.out.println(entityManager.contains(emp1));
        Employee emp2 = new Employee(2, "Suresh", "New Delhi");
        System.out.println(entityManager.contains(emp2));
        entityManager.close();
        JPAUtilityHandler.close();
        System.out.println("Done");
    }

    public static void main1(String[] args) {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp = entityManager.find(Employee.class, 1);
        System.out.println("Contains(before detach):" + entityManager.contains(emp));
        //Detach entity
        entityManager.detach(emp);
        System.out.println("Contains(After detach):" + entityManager.contains(emp));
        entityManager.close();
        JPAUtilityHandler.close();
        System.out.println("Entity detached.");
    }

    public static void main2(String[] args) {
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
        System.out.println("Entity merged.");
    }

    public static void main3(String[] args) {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        Employee emp = entityManager.find(Employee.class, new Integer(1));
        //start removing
        entityManager.getTransaction().begin();
        entityManager.remove(emp);
        entityManager.getTransaction().commit();
        entityManager.close();
        JPAUtilityHandler.close();
        System.out.println("Entity removed.");
    }

    public static void main4(String[] args) {
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
        System.out.println("Entity updated.");
    }

    public static void main5(String[] args) {
        EntityManager entityManager = JPAUtilityHandler.getEntityManager();
        entityManager.getTransaction().begin();
        Employee employee = new Employee(1, "Mahesh", "Varanasi");
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.close();
        JPAUtilityHandler.close();
        System.out.println("Entity saved.");
    }
}
