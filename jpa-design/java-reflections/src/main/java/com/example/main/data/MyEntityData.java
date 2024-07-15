package com.example.main.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class MyEntityData {

    @Id
    private int id;
}
