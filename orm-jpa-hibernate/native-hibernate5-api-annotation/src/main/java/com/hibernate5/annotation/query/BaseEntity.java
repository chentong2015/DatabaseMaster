package com.hibernate5.annotation.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_entity_sample")
public class BaseEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "label")
    private String label;

    @Column(name = "count")
    private double count;

    public BaseEntity() {
    }

    public BaseEntity(int id, String label, double count) {
        this.id = id;
        this.label = label;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "EntitySample{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", count=" + count +
                '}';
    }
}
