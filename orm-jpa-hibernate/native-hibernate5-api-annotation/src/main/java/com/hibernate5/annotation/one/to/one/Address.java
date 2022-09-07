package com.hibernate5.annotation.one.to.one;

import javax.persistence.*;

@Entity
@Table(name = "t_address_1")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    // 双向一对一，选择任何一端作为维护端
    @OneToOne(mappedBy = "address")
    private User user;

    public Address() {
    }

    public Address(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}