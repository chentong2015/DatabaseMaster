package com.hibernate5.annotation.fetching.fetching_batch;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "com.hibernate5.annotation.fetching_batch.Employee")
@Table(name = "t_fetching_batch_employee")
public class Employee {

    @Id
    private Long id;

    @NaturalId
    private String name;

    public Employee() {
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
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
