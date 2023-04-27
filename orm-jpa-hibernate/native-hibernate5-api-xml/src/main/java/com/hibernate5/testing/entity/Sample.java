package com.hibernate5.testing.entity;

public class Sample {

    private long id;
    private int value;

    public Sample() {
    }

    public Sample(long id, int value) {
        this.id = id;
        this.value = value;
    }

    public final long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
