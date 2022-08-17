package com.hibernate.entity.manager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "com.hibernate.entity.manager.entity.Sample")
@Table(name = "t_sample_entity")
public class Sample {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name", length = 255)
    private String name;

    public Sample() {
    }

    public Sample(int id, String name) {
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

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
