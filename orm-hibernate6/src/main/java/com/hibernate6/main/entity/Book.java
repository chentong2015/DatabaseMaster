package com.hibernate6.main.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity // Mark a class as an entity 对应到<class/> mapping
@Table(name = "t_book") // Specifies the table name 默认的名称是BOOK
public class Book {

    // TODO. GenerationType.IDENTITY 从0开始依次增加, 支持Psql
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    // @GenericGenerator(name = "native", strategy = "native") 随机自动地增加
    private long id;

    // String对应到psql中的类型为character varying可变字符串
    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    public Book() {
    }

    public Book(long id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
