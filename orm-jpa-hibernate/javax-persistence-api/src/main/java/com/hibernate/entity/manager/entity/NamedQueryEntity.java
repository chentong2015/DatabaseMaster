package com.hibernate.entity.manager.entity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

// TODO. 直接在entity class类型上声明query查询语句，通过name名称来直接调用
@Entity
@Table(name = "t_named_query_table")
// 可以设定一组集合的具名查询
// @NamedQueries({@NamedQuery(name = "EntityNamed.findAll", query = "xxx"),
//                @NamedQuery(name = "EntityNamed.findByUrl", query = "xxx")})
@NamedQuery(name = "EntityNamed.findAll",
        query = "SELECT en FROM NamedQueryEntity en")
@NamedQuery(name = "EntityNamed.findByUrl",
        query = "SELECT en FROM NamedQueryEntity en WHERE en.url = :url")
// 这里的具名Native查询语句必须声明返回的结果类型
@NamedNativeQuery(name = "EntityNamedNative.findAll",
        query = "SELECT * FROM t_named_query_table",
        resultClass = NamedQueryEntity.class)
public class NamedQueryEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "url")
    private String url;

    @Column(name = "name")
    private String name;

    public NamedQueryEntity() {
    }

    public NamedQueryEntity(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NamedQueryEntity{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
