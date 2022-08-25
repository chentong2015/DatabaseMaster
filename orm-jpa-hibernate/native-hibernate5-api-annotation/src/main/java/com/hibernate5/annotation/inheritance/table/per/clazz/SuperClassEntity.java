package com.hibernate5.annotation.inheritance.table.per.clazz;

import javax.persistence.*;

// TODO. 如果是针对super class抽象类来进行查询，则会对它的所有子表进行Union Select查询，得到全局的数据结果
//   Query<SuperClassEntity> query = session.createQuery("From " + SuperClassEntity.class.getName(), SuperClassEntity.class);
//   List<SuperClassEntity> resultList = query.getResultList();
//
// Hibernate:
//    select
//        superclass0_.id as id1_1_,
//        superclass0_.name as name2_1_,
//        superclass0_.name1 as name1_16_,
//        superclass0_.name2 as name1_17_,
//        superclass0_.clazz_ as clazz_
//    from
//        ( select
//            id,
//            name,
//            name1,
//            null::varchar as name2,
//            1 as clazz_
//        from
//            t_table_per_clazz_s1
//        union all
//        select
//            id,
//            name,
//            null::varchar as name1,
//            name2,
//            2 as clazz_
//        from
//            t_table_per_clazz_s2
//    ) superclass0_
//
// 由于抽象的母类没有对应的映射表，所以得到的是子表的合并数据
// SuperClassEntity{id=1, name='name s1'}
// SuperClassEntity{id=12, name='name s2'}
@Entity(name = "com.hibernate5.annotation.inheritance.table.per.clazz.SuperClassEntity")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SuperClassEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "name", length = 32)
    private String name;

    public SuperClassEntity() {
    }

    public SuperClassEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SuperClassEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
