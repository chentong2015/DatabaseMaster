package com.hibernate5.annotation.fetching.fetching_batch;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "com.hibernate5.annotation.fetching_batch.Department")
@Table(name = "t_fetching_batch_department")
public class Department {

    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 3)
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
