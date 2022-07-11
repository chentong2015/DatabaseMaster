package com.jpa.basic.entity;

// 在Entity Class上不使用任何JPA注解
// 只需hibernate mapping xml文件中配置相关的映射信息到database中的table行
public class Book {

    private long id;
    private String name;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
