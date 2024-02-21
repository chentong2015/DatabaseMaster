package com.example.main;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "jpa-entity-locations")
@XmlAccessorType(XmlAccessType.NONE)
public class JpaEntityLocations {

    @XmlElement(name = "package")
    private List<String> packages;

    public List<String> getPackages() {
        return packages;
    }
}