package com.hibernate5.annotation.any.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_property_integer")
public class IntegerProperty implements MyProperty<Integer> {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private Integer value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
