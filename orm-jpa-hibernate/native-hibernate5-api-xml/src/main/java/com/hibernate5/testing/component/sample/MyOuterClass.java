package com.hibernate5.testing.component.sample;

public class MyOuterClass {

    private long id;
    private String name;
    private Component component;

    public MyOuterClass() {
    }

    public MyOuterClass(long id, String name, Component component) {
        this.id = id;
        this.name = name;
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

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
