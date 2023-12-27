package com.hibernate5.annotation.inheritance.mapper.superclass;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "CreditAccount")
public class CreditAccount extends Account {

    private BigDecimal creditLimit;

    //Getters and setters are omitted for brevity
}