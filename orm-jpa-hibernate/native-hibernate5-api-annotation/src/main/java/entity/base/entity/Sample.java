package entity.base.entity;

import javax.persistence.*;

// TODO. 可以将@Column注解写在getter方法, 而不需要标注在属性上
@Entity(name = "jpa.annotations.entity.entity.Sample")
@Table(name = "t_sample")
public class Sample {

    private int id;
    private String firstname;
    private String lastname;
    private String twitter;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "firstname", length = 255)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", length = 255)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "twitter", length = 15)
    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", twitter='" + twitter + '\'' +
                '}';
    }
}
