package com.hibernate5.annotation.join.JoinColumnOrFormula;

import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;

@Entity(name = "com.hibernate5.annotation.join.column.User")
@Table(name = "t_citizen")
public class Citizen {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "language")
    private String language;

    // TODO. 在查询User时会根据指定的列或者公式来获取Country数据
    @ManyToOne
    @JoinColumnOrFormula(column = @JoinColumn(
            name = "language", // 当前表的列的名称
            referencedColumnName = "primaryLanguage", // 引用的列的名称(不是字段的名称)
            insertable = false,
            updatable = false))
    @JoinColumnOrFormula(formula = @JoinFormula(
            value = "true",    // 对应公式的值
            referencedColumnName = "is_default"))
    private Country country;

    public Citizen() {
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Country getCountry() {
        return country;
    }
}
