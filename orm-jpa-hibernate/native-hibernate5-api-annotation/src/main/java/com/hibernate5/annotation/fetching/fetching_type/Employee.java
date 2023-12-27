package com.hibernate5.annotation.fetching.fetching_type;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "com.hibernate5.annotation.fetching.fetching_type.Employee")
@Table(name = "t_fetching_type_employee")
public class Employee {

    @Id
    private Long id;

    @NaturalId
    private String username;

    public Employee() {
    }

    public Employee(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
