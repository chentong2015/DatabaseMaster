package com.hibernate5.annotation.entity;

import com.hibernate5.annotation.entity.annotations.ColumnDescription;
import com.hibernate5.annotation.entity.annotations.TableDescription;

import javax.persistence.*;

// TODO. 不同的数据库schema默认值不同，可以自定义设置
@Entity(name = "com.hibernate5.annotation.entity.MyEntity")
@Table(name = "t_first_entity", schema = "public")
@TableDescription(shortDescription = "short table", longDescription = "table long description")
public class MyEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    @ColumnDescription(shortDescription = "short column")
    private double code;

    // Specify that an entity attribute represents an enumerated type.
    // EnumType.STRING 设置将枚举类型的值持久化成String字符串
    @Enumerated(EnumType.STRING)
    @Column(name = "category_type")
    private CategoryType categoryType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCode() {
        return code;
    }

    public void setCode(double code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
