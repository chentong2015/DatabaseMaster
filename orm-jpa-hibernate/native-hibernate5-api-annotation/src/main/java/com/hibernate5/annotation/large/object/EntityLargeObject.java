package com.hibernate5.annotation.large.object;

import javax.persistence.*;

@Entity
@Table(name = "t_large_object_table")
public class EntityLargeObject {

    @Id
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "name")
    private String name;

    // TODO. 注意LOB
    //  1. 大对象的存储操作需要放到transaction事务中，因为它可能是一个耗时的操作 !!
    //     A large object can be stored in several records, that's why you have to use a transaction.
    //  2. Hibernate默认会将LOB大对象映射到PostgresSQL的oid数据类型，该类型在Repository层操作时很可能报错
    @Lob
    @Column(name = "json_data", nullable = false)
    private String jsonData;

    public EntityLargeObject() {
    }

    public EntityLargeObject(String name, String jsonData) {
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
