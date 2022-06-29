package com.hibernate5.testing.any;

public class MyAnyClass {

    private long id;
    private String name;
    private Object anyClassModel;
    private Component component;

    public MyAnyClass() {
    }

    public MyAnyClass(long id, String name, Object anyClassModel, Component component) {
        this.id = id;
        this.name = name;
        this.anyClassModel = anyClassModel;
        this.component = component;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getAnyClassModel() {
        return anyClassModel;
    }

    public void setAnyClassModel(Object anyClassModel) {
        this.anyClassModel = anyClassModel;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
