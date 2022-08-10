package entity.component;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// TODO. @Embeddable注解的含义 ==> 等效于hbm.xml Mapping映射中的<component>
//    https://en.wikibooks.org/wiki/Java_Persistence/Embeddables
// 1. The @Embeddable annotation allows to specify a class
//    whose instances are stored as intrinsic part of the owning entity.
// 2. By default, column definitions specified in the @Embeddable class
//    apply to the table of the owning entity
@Embeddable
public class ComponentAddress {

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "countty")
    private String country;

    @Column(name = "pincode")
    private String pincode;

    public ComponentAddress() {
    }

    public ComponentAddress(String street, String city, String country, String pincode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
