package com.hibernate5.annotation.join.JoinColumn.OneToMany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_computer_software")
public class Software {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public Software() {
    }

    public Software(int id, String name) {
        this.id = id;
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
