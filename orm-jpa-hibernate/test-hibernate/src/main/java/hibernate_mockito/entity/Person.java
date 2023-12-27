package hibernate_mockito.entity;

import javax.persistence.*;

@Entity
@Table(name = "Person")
public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private String street;
    private int houseNumber;
    private String tel;
    private String email;
    private String mobile;
    private Account account;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, String street,
                  int houseNumber, String tel, String email, String mobile,
                  Account account) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.tel = tel;
        this.email = email;
        this.mobile = mobile;
        this.account = account;
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

    @Column(name = "Firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Column(name = "Lastname")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "Street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "Housenumber")
    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Column(name = "Tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "Mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}