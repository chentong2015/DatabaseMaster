package entity.inheritance.table.per.clazz;

import javax.persistence.*;


@Entity
@Table(name = "t_super_inheritance_table")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SuperClassEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "name", length = 32)
    private String name;

    public SuperClassEntity() {
    }

    public SuperClassEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
