package jpa.annotations.entity.any;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VehicleOwner {

    @Id
    private int owner_id;

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }
}
