package hibernate_mockito.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Random;
import java.util.TreeSet;

@Entity
@Table(name = "Account")
public class Account {

    public static Random rnd = new Random();
    private int id;
    private String username;
    private long password;
    private long salt;
    private Person person;
    private String role;
    private Collection<Account> friendlist;

    public Account() {
    }

    public Account(String username, long password, Person person, String role) {
        super();
        this.username = username;
        this.password = password;
        this.setSalt();
        this.person = person;
        this.role = role;
        this.friendlist = new TreeSet<Account>();
    }

    @Id
    @GeneratedValue
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "Password")
    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = (long) salt + password.hashCode();
    }

    @Column(name = "Salt")
    public long getSalt() {
        return salt;
    }

    public void setSalt(long salt) {
        this.salt = salt;
    }

    public void setSalt() {
        this.salt = rnd.nextLong();
        if (this.salt < 0) {
            this.salt = this.salt * (-1);
        }
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Person_FK")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Column(name = "Role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
    }
}