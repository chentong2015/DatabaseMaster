package com.hibernate5.annotation.fetching.sub_select;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "com.hibernate5.annotation.fetching.sub_select.Order")
@Table(name = "t_fetching_sub_select_order")
public class Order {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    public Order() {
    }

    public Order(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
