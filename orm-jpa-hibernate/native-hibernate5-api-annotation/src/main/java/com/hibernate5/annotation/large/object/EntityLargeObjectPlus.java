package com.hibernate5.annotation.large.object;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "t_large_object_table_plus")
public class EntityLargeObjectPlus {

    @Id
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "name")
    private String name;

    // TODO. @Type使用指定的Hibernate Type将映射到PostgresSQL的oid类型转成varchar()类型
    @Lob
    @Column(name = "json_data", nullable = false)
    @Type(type = "org.hibernate.type.StringType")
    private String jsonData;

    public EntityLargeObjectPlus() {
    }

    public EntityLargeObjectPlus(String name, String jsonData) {
        this.name = name;
        this.jsonData = jsonData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
