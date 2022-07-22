package jpa.annotations.entity.inheritance;

import javax.persistence.*;

// TODO. JPA设置Entity Class之间的继承关系 => 替代xml文件配置的继承关系
// 1. SINGLE_TABLE: A single table per class hierarchy
// 2. TABLE_PER_CLASS: A table per concrete entity class
// 3. JOINED: 继承链上公共的数据会存储到separate table, a join is performed to instantiate the subclass
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SuperClassEntity {

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
}
