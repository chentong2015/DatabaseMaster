package com.hibernate5.annotation.inheritance.table.per.clazz;

import javax.persistence.*;

@Entity
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
}
