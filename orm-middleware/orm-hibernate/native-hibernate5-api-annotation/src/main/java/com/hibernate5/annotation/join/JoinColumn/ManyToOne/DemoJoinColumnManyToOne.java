package com.hibernate5.annotation.join.JoinColumn.ManyToOne;

import com.hibernate5.annotation.join.JoinColumnOrFormula.Citizen;
import com.hibernate5.annotation.join.JoinColumnOrFormula.Country;
import org.hibernate.Session;

public class DemoJoinColumnManyToOne {

    // TODO. 更新数据库一定要放置到transaction事务中，开启并提交
    public static void testManyToOne(Session session) {
        session.getTransaction().begin();
        Type type1 = new Type("type name");
        // 由于级联操作，这里可以不需要手动操作
        // session.persist(type1);

        WebPage closingEntity1 = new WebPage("lable1 name");
        closingEntity1.setType(type1);
        WebPage closingEntity2 = new WebPage("label2 name");
        closingEntity2.setType(type1);
        session.persist(closingEntity1);
        session.persist(closingEntity2);

        session.getTransaction().commit();
    }

    public static void testJoinColumn(Session session) {
        session.getTransaction().begin();
        Country US = new Country();
        US.setId(1);
        US.setDefault(true);
        US.setPrimaryLanguage("English");
        US.setName("United States");
        session.persist(US);

        Country Romania = new Country();
        Romania.setId(40);
        Romania.setDefault(true);
        Romania.setName("Romania");
        Romania.setPrimaryLanguage("Romanian");
        session.persist(Romania);

        Citizen user1 = new Citizen();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLanguage("English");
        session.persist(user1);

        Citizen user2 = new Citizen();
        user2.setId(2L);
        user2.setFirstName("Vlad");
        user2.setLanguage("Romanian");
        session.persist(user2);
        session.getTransaction().commit();
    }

    //  select
    //      citizen0_.id as id1_1_0_,
    //      citizen0_.language as language3_1_0_,
    //      citizen0_.first_name as first_na2_1_0_,
    //      true as formula2_0_,
    //      country1_.id as id1_3_1_,
    //      country1_.is_default as is_defau2_3_1_,
    //      country1_.name as name3_3_1_,
    //      country1_.primaryLanguage as primaryl4_3_1_
    //  from
    //      t_citizen citizen0_
    //  left outer join t_country country1_
    //      on citizen0_.language=country1_.primaryLanguage 自定义的join列的匹配条件
    //      and true=country1_.is_default 自定义Formula的判断条件
    //  where
    //      citizen0_.id=?  提供主键参数的id
    public static void testJoinColumnFetching(Session session) {
        Citizen citizen1 = session.find(Citizen.class, 1L);
        System.out.println(citizen1.getCountry().getName());

        Citizen citizen2 = session.find(Citizen.class, 2L);
        System.out.println(citizen2.getCountry().getName());
    }
}
