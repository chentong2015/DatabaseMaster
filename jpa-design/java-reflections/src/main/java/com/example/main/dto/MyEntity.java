package com.example.main.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class MyEntity {

    @Id
    private int id;
}
