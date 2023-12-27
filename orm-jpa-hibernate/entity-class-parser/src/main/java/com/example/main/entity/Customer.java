package com.example.main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_customer")
public class Customer {

    @Id
    @GeneratedValue
    private long id;
}
