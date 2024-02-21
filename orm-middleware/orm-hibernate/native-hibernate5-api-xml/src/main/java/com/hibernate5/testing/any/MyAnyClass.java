package com.hibernate5.testing.any;

public class MyAnyClass {

    private long id;
    private String name;
    private Object anyClassModel;

    public MyAnyClass() {
    }
 
    public MyAnyClass(long id, String name, Object anyClassModel) {
        this.id = id;
        this.name = name;
        this.anyClassModel = anyClassModel;
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
}
