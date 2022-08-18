package com.hibernate5.annotation.join.fetching;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "com.hibernate5.annotation.join.fetching.Department")
@Table(name = "t_department_2")
public class Department {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    // TODO. FetchMode.SELECT标明在查询一对多关联的表时，关联的表使用什么样的方式来fetching数据
    //       FetchType.LAZY 如果不调用属性获取数据，则不会按照FetchMode来执行数据fetching操作
    //       FetchType.EAGER 从数据库中获取Department对象时，立即执行fetching操作(无论数据是否被使用)
    // The association is going to be fetched using a secondary select for each individual entity
    // This mode can be used for either FetchType.EAGER or FetchType.LAZY
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    // @Fetch(FetchMode.SELECT) 这里需要重新建表才能生效
    @Fetch(FetchMode.SUBSELECT)
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

    public Department(Long id, String name) {
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
