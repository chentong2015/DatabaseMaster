package jpa.annotations.entity.any;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

// TODO. Polymorphic Relationships 构建一种多态的关系
@Entity
@DiscriminatorValue("VEH")
public class Vehicle {

    private Long id;
    private VehicleOwner vehicleOwner;

    // 车辆表这将产生一个二元组(owner_type，owner_id)
    // 共同确定VehicleOwner对象是Person还是Company
    @Any(
            metaColumn = @Column(name = "owner_type", length = 3), fetch = FetchType.LAZY
    )
    @AnyMetaDef(
            idType = "long", metaType = "string", metaValues = {
            @MetaValue(targetEntity = Person.class, value = "PRS"),
            @MetaValue(targetEntity = Company.class, value = "CPY")
    })
    @JoinColumn(name = "owner_id")  // 级联指定数据表的id字段
    public VehicleOwner getOwner() {
        return vehicleOwner;
    }

    public void setOwner(VehicleOwner owner) {
        this.vehicleOwner = owner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
