package com.hibernate6.main.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

// TODO. 注解@Entity指明这个类是遵循orm映射的类, 一个实体Bean, 每个实例代表数据表中的一行数据
//       注解@Table指定Entity所要映射带数据库表, 如果缺少, 则系统默认采用类名作为映射表的表名
// 1. Entity(name)名称默认取的是class类型的名称, 不能是Java Persistence查询中的保留文字
// 2. Entity(name)名称用于在HQL查询中对entity引用, 如果有两个相同的名称将会造成冲突
// 3. 配置.hbm.xml可以将同一个Entity映射到数据库中不同的表
@Entity(name = "t_demo_table")
@Table(name = "t_demo_table", schema = "public")
public class EntityDemo {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Object id;

    @Column(name = "name")
    private String name;

    // TODO: 使用Version来实现乐观锁
    @Version
    private Integer version;

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
