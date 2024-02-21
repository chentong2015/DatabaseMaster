package com.hibernate5.testing.component.collections;

import java.util.Set;

public class Developer {

    private Long id;
    private String name;
    private Set<Name> someNames;

    public Developer() {
    }

    public Developer(Long id, String name, Set<Name> someNames) {
        this.id = id;
        this.name = name;
        this.someNames = someNames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Name> getSomeNames() {
        return someNames;
    }

    public void setSomeNames(Set<Name> someNames) {
        this.someNames = someNames;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", someNames=" + someNames +
                '}';
    }
}
