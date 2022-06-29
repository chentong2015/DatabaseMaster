package jpa.api.any.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CPY")
public class Company extends Vehicle {
}
