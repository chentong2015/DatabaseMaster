package com.hibernate5.testing.many_to_one_master;

public class Asset {

    private double reference;

    private String label;

    public Asset() {
    }

    public Asset(double reference, String label) {
        this.reference = reference;
        this.label = label;
    }

    public double getReference() {
        return reference;
    }

    public void setReference(double reference) {
        this.reference = reference;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
