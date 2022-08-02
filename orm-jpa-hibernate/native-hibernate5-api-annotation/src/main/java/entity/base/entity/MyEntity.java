package entity.base.entity;

import javax.persistence.*;

@Entity(name = "entity.base.entity.MyEntity")
@Table(name = "t_first_entity")
public class MyEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private double code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCode() {
        return code;
    }

    public void setCode(double code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
