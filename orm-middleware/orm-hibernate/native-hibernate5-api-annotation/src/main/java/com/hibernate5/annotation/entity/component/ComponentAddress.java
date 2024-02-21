package com.hibernate5.annotation.entity.component;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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

    // TODO. 标记@Embeddable的类型应该带有无参构造器 [public, protected] no-arg constructor
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
