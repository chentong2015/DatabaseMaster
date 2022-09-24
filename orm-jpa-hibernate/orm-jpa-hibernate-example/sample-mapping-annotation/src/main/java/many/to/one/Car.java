package many.to.one;

import jakarta.persistence.*;

@Entity
@Table(name = "t_car")
public class Car {

    @Id
    @GeneratedValue(generator = "increment")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "person_id")
    private Person person;

    public Car() {
    }

    public Car(String name, Person person) {
        this.name = name;
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", person=" + person +
                '}';
    }
}
