package com.hibernate5.testing.any_master.entities;

public class FilterItem {

    private double reference;
    private String parentType;
    private int parentReference;

    // 多态: 任意的object对象可以映射到任意的table的主键，进行自定义关联
    private Object objectInstance;

    public FilterItem() {
        // For hibernate using
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
