package com.hibernate5.testing.any.model2;

public class ClassModel2 {

    private double id;
    private String name2;

    public ClassModel2() {
    }

    public ClassModel2(double id, String name2) {
        this.id = id;
        this.name2 = name2;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
