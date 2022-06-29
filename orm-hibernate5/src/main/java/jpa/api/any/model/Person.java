package jpa.api.any.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PRS")
public class Person extends Vehicle {
}
