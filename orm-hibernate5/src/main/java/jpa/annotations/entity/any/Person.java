package jpa.annotations.entity.any;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PRS")
public class Person extends Vehicle {
}
