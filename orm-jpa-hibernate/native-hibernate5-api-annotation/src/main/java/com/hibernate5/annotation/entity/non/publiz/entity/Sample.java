package com.hibernate5.annotation.entity.non.publiz.entity;

import javax.persistence.*;

// 1. Entity Class并不需要设置成public
// 2. Entity Class可以设置成final class
// 3. Entity Class可以设置成abstract class 用于继承关系
@Entity(name = "jpa.annotations.entity.entity.Sample")
@Table(name = "t_sample")
final class Sample {

    private int id;
    private String firstname;
    private String lastname;
    private String twitter;

    // TODO. 等效于将注解写在getter方法上
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "firstname", length = 255)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", length = 255)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "twitter", length = 15)
    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", twitter='" + twitter + '\'' +
                '}';
    }
}
