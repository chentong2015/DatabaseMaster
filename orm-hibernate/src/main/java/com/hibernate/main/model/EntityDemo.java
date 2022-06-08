package com.hibernate.main.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

// TODO. 可以自定义Entity实体的名称，该名称用于在查询中对entity的引用
//       不能是Java Persistence查询中的保留文字
// 1. Entity(name)名称默认取的是class类型的名称
// 2. 如果这里的实体名称一致，该如何处理 ?
@Entity(name = "my-entity-table")
@Table(name = "t_demo", schema = "public")
public class EntityDemo {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Object id;

    @Column(name = "name")
    private String name;

    // TODO. 必须提供Getter&Setter方法，满足在HBM mapping文件中的属性配置
    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
