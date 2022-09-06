package com.hibernate5.annotation.fetching.select;

import javax.persistence.*;

@Entity(name = "com.hibernate5.annotation.fetching.select.Order")
@Table(name = "t_fetching_order")
public class Order {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    // 适用Customer类型对应表的id来唯一Fetch
    // 定义Order对应表的列名称"customer_id"
    @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "customer_id")
    private Customer customer;

    public Order() {
    }

    public Order(Long id, String name, Customer customer) {
        this.id = id;
        this.name = name;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customer=" + customer +
                '}';
    }
}
