package com.hibernate5.annotation.join.JoinColumn.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_computer")
public class Computer {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    // 1. targetEntity 目标的实体类型可以不设置，根据field属性确定类型
    // 2. referencedColumnName 指定引用的关联表的列名称，默认使用主键
    @OneToMany(fetch = FetchType.EAGER, targetEntity = Software.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "computer_id", referencedColumnName = "id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Software> softwareSet;

    public Computer() {
    }

    public Computer(int id, String name, Set<Software> softwareSet) {
        this.id = id;
        this.name = name;
        this.softwareSet = softwareSet;
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

    public Set<Software> getSoftwareSet() {
        return softwareSet;
    }

    public void setSoftwareSet(Set<Software> softwareSet) {
        this.softwareSet = softwareSet;
    }
}
