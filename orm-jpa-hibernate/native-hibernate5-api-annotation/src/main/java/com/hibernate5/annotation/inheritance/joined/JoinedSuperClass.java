package com.hibernate5.annotation.inheritance.joined;

import javax.persistence.*;

@Entity
@Table(name = "t_a_joined_super_class")
@Inheritance(strategy = InheritanceType.JOINED)
public class JoinedSuperClass {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

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
