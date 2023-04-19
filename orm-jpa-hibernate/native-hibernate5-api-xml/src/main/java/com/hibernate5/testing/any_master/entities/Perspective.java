package com.hibernate5.testing.any_master.entities;

import java.util.Set;

public class Perspective {

    private double reference;
    private String description;
    private Set<FilterItem> filterItems;

    public Perspective() {
    }

    public double getReference() {
        return reference;
    }

    public void setReference(double reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<FilterItem> getFilterItems() {
        return filterItems;
    }

    public void setFilterItems(Set<FilterItem> filterItems) {
        this.filterItems = filterItems;
    }
}
