package entity.any;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CPY")
public class Company extends Vehicle {
}
