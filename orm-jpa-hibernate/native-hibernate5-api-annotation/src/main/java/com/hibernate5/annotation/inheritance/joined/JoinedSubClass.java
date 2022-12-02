package com.hibernate5.annotation.inheritance.joined;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_a_test_sub_class")
public class JoinedSubClass extends JoinedSuperClass {

    @Column(name = "sub_name")
    private String subName;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
