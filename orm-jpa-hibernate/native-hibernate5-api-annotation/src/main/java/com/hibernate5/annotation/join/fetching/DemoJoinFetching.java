package com.hibernate5.annotation.join.fetching;

import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;

public class DemoJoinFetching {

    public static void initTables(Session session) {
        session.getTransaction().begin();
        Employee employee1 = new Employee(1L, "test1");
        Employee employee2 = new Employee(2L, "test2");
        Department department1 = new Department(1L, "DepartmentJava");

        department1.setEmployees(Arrays.asList(employee1, employee2));
        employee1.setDepartment(department1);
        employee2.setDepartment(department1);

        session.persist(employee1);
        session.persist(employee2);
        session.persist(department1);
        session.getTransaction().commit();
    }

    // TODO. FetchMode.SELECT底层执行的SQL查询语句，根据department的id在另一个表中执行另外select语句
    // Hibernate:
    //     select
    //         department0_.id as id1_3_
    //     from
    //         t_department_1 department0_
    // Fetched Departments: 1
    //
    // Hibernate:
    //     select
    //         employees0_.department_id as departme3_4_0_,
    //         employees0_.id as id1_4_0_,
    //         employees0_.id as id1_4_1_,
    //         employees0_.department_id as departme3_4_1_,
    //         employees0_.username as username2_4_1_
    //     from
    //         t_employee_1 employees0_
    //     where
    //         employees0_.department_id=?
    // 2
    public static void testSelectFetchMode(Session session) {
        String hqlQuery = "FROM com.hibernate5.annotation.join.fetching.Department";
        List<Department> departments = session.createQuery(hqlQuery, Department.class).getResultList();
        System.out.println("Fetched Departments: " + departments.size());
        for (Department department : departments) {
            System.out.println(department.getEmployees().size());
        }
    }

    // TODO. FetchMode.SUBSELECT
    // SELECT
    //    d.id as id1_0_
    // FROM
    //     Department d
    // where
    //     d.name like 'Department%'
    // Fetched Departments: 1
    //
    // SELECT
    //     e.department_id as departme3_1_1_,
    //     e.id as id1_1_1_,
    //     e.id as id1_1_0_,
    //     e.department_id as departme3_1_0_,
    //     e.username as username2_1_0_
    // FROM
    //     Employee e
    // WHERE
    //     e.department_id in (
    //         SELECT
    //             fetchmodes0_.id
    //         FROM
    //             Department fetchmodes0_
    //         WHERE
    //             d.name like 'Department%'
    //     )
    public static void testSubSelectFetchMode(Session session) {
        String hqlQuery = "select d from com.hibernate5.annotation.join.fetching.Department d where d.name like :token";
        List<Department> departments = session.createQuery(hqlQuery, Department.class)
                .setParameter("token", "Department%")
                .getResultList();
        System.out.println("Fetched Departments: " + departments.size());
        for (Department department : departments) {
            System.out.println(department.getEmployees().size());
        }
    }

    // TODO. FetchMode.JOIN使用join来联合表查询
    // SELECT
    //     d.id as id1_0_0_,
    //     e.department_id as departme3_1_1_,
    //     e.id as id1_1_1_,
    //     e.id as id1_1_2_,
    //     e.department_id as departme3_1_2_,
    //     e.username as username2_1_2_
    // FROM
    //     Department d
    // LEFT OUTER JOIN
    //     Employee e
    //        on d.id = e.department_id
    // WHERE
    //     d.id = 1
    //
    // -- Fetched department: 1
}