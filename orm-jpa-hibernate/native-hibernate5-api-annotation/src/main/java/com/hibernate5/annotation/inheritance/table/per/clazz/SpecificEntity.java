package com.hibernate5.annotation.inheritance.table.per.clazz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "com.hibernate5.annotation.inheritance.table.per.clazz.SpecificEntity")
@Table(name = "t_table_per_clazz_s1")
public class SpecificEntity extends SuperClassEntity {

    @Column(name = "name1")
    private String specificName1;

    public String getSpecificName1() {
        return specificName1;
    }

    public void setSpecificName1(String specificName1) {
        this.specificName1 = specificName1;
    }
}
