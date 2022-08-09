package com.example.main.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_component")
public class Component {

    @Id
    private long id;
}
