package com.hibernate5.annotation.polymorphism;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_test_book")
@Polymorphism(type = PolymorphismType.IMPLICIT)
public class Book implements DomainModelEntity {

    @Id
    private int id;

    @Override
    public int getId() {
        return 0;
    }
}
