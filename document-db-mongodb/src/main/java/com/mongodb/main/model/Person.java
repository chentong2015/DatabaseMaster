package com.mongodb.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data     // 标准POJO的类型声明
@Document // 面向MongoDB的一个Document，相当于关系型数据库中的Entity
@AllArgsConstructor
public class Person {

    @Id
    private String nickname;
    private String email;
}
