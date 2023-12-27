package com.hibernate5.annotation.join.JoinColumn.OneToMany;

import org.hibernate.Session;

import java.util.HashSet;
import java.util.Set;

public class DemoJoinColumnOneToMany {

    // cascade = CascadeType.ALL 配置级联操作，同时存储关联表的信息
    // 1. insert into t_computer (name, id) values (?, ?)          先存储主表的信息
    // 2. insert into t_computer_software (name, id) values (?, ?) 然后存储关联表的信息
    // 3. update t_computer_software set software_id=? where id=?  最后来更新主表的外键值
    public static void initTable(Session session) {
        session.getTransaction().begin();

        Software software1 = new Software(11, "name 11");
        Software software2 = new Software(22, "name 21");

        Set<Software> softwareSet = new HashSet<>();
        softwareSet.add(software1);
        softwareSet.add(software2);
        Computer computer = new Computer(2, "apple", softwareSet);
        session.persist(computer);

        session.getTransaction().commit();
    }

    // FetchType.EAGER 配置fetching的类型为EAGER，则在查询computer时便会取出关联的software数据
    // Hibernate:
    //    select
    //        computer0_.id as id1_3_0_,
    //        computer0_.name as name2_3_0_,
    //        softwarese1_.software_id as software3_4_1_,
    //        softwarese1_.id as id1_4_1_,
    //        softwarese1_.id as id1_4_2_,
    //        softwarese1_.name as name2_4_2_
    //    from
    //        t_computer computer0_
    //    left outer join t_computer_software softwarese1_
    //         on computer0_.id=softwarese1_.software_id
    //    where
    //        computer0_.id=?
    public static void getObjects(Session session) {
        Computer computer = session.get(Computer.class, 1);
        System.out.println(computer.getName());
        System.out.println("----------------");

        for (Software software : computer.getSoftwareSet()) {
            System.out.println(software.getName());
        }
    }
}

