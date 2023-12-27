package com.hibernate5.annotation.inheritance.mapper.superclass;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "DebitAccount")
public class DebitAccount extends Account {

    private BigDecimal overdraftFee;

    //Getters and setters are omitted for brevity

}
