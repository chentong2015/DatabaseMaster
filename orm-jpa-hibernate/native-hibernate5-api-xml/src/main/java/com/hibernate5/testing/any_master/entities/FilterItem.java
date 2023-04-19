package com.hibernate5.testing.any_master.entities;

public class FilterItem {

    private double reference;
    private String parentType;
    private int parentReference;
    private Object objectInstance;

    public FilterItem() {
    }

    public double getReference() {
        return reference;
    }

    public void setReference(double reference) {
        this.reference = reference;
    }

    public int getParentReference() {
        return parentReference;
    }

    public void setParentReference(int parentReference) {
        this.parentReference = parentReference;
    }

    public Object getObjectInstance() {
        return objectInstance;
    }

    public void setObjectInstance(Object objectInstance) {
        this.objectInstance = objectInstance;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }
}
