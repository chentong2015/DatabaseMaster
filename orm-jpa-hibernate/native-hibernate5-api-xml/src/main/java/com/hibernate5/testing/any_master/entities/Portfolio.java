package com.hibernate5.testing.any_master.entities;

public class Portfolio {

    private double reference;
    private String description;

    public Portfolio() {
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
}
