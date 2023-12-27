package com.hibernate5.testing.any.model1;

public class ClassModel1 {

    private double id;
    private String name1;

    public ClassModel1() {
    }

    public ClassModel1(double id, String name1) {
        this.id = id;
        this.name1 = name1;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }
}
