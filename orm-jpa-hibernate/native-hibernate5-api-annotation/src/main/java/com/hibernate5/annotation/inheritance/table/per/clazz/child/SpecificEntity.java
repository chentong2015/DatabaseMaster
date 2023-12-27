package com.hibernate5.annotation.inheritance.table.per.clazz.child;

import com.hibernate5.annotation.inheritance.table.per.clazz.SuperClassEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "com.hibernate5.annotation.inheritance.table.per.clazz.child.SpecificEntity")
@Table(name = "t_table_per_clazz_s2")
public class SpecificEntity extends SuperClassEntity {

    @Column(name = "name2")
    private String specificName2;

    public String getSpecificName2() {
        return specificName2;
    }

    public void setSpecificName2(String specificName2) {
        this.specificName2 = specificName2;
    }
}
