package com.hibernate.main.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

// 对于数据库中的一个实体表
@Entity
@Table(name = "PERSON")
public class Person {

    // TODO. 使用自定义的ID主键的生成规则，来自hibernate的扩展
    @GenericGenerator(
            name = "personIdGenerator",
            strategy = "com.ctong.idgenerator.StoredProcedureIdGenerator",
            parameters = {
                    @Parameter(name = "UID1", value = "STATICS"),
                    @Parameter(name = "UID2", value = "CATEGORY")
            }
    )
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "personIdGenerator")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
