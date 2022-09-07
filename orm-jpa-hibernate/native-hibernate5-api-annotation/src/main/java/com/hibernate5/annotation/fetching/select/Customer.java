package com.hibernate5.annotation.fetching.select;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "com.hibernate5.annotation.fetching.select.Customer")
@Table(name = "t_fetching_customer")
public class Customer {

    @Id
    private Long id;

    // 交给Many-端来维护关系，避免执行update语句
    // fetch = FetchType.EAGER 在获取Customer数据的时候，选择"立即获取"Order列表
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @Fetch(value = FetchMode.SELECT)
    // @BatchSize(size = 10)
    private Set<Order> orders = new HashSet<>();

    public Customer() {
    }

    public Customer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", orders=" + orders +
                '}';
    }
}
