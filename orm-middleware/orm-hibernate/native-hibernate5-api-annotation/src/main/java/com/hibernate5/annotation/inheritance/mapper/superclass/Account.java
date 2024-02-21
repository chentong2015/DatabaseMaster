package com.hibernate5.annotation.inheritance.mapper.superclass;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

// @MappedSuperclass:
// 1. 属于JPA的映射注解, 没有@Entity annotation, 该类型的数据不会持久化到数据库中
// 2. 该注解表明该类型是被其他类型继承，并且其他的entity映射的table中包含该类型定义的所有信息
@MappedSuperclass
public class Account {

    @Id
    private Long id;

    private String owner;

    private BigDecimal balance;

    private BigDecimal interestRate;

    //Getters and setters are omitted for brevity
}
