package entity.embeded.component;

import javax.persistence.*;

//  对应到数据库中创建的表格
//  create table t_staff (
//     id int4 not null,
//     city varchar(255),
//     countty varchar(255),
//     postcode varchar(255),
//     streetAddr varchar(255),
//     first_name varchar(255),
//     last_name varchar(255),
//     middle_name varchar(255),
//     primary key (id)
//  )
@Entity
@Table(name = "t_staff")
public class Staff {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Embedded
    private ComponentPersonName name;

    // TODO. @AttributeOverride进行对嵌入的class的列名称进行修改
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "street", column = @Column(name = "streetAddr")),
            @AttributeOverride(name = "pincode", column = @Column(name = "postcode"))
    })
    private ComponentAddress address;

    public Staff() {
    }

    public Staff(ComponentPersonName name, ComponentAddress address) {
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ComponentPersonName getName() {
        return name;
    }

    public void setName(ComponentPersonName name) {
        this.name = name;
    }

    public ComponentAddress getAddress() {
        return address;
    }

    public void setAddress(ComponentAddress address) {
        this.address = address;
    }
}
