package com.hibernate5.testing.component.collections;

public class Name {

    private char initial;
    private String first;
    private String last;

    public Name() {
    }

    public Name(char initial, String first, String last) {
        this.initial = initial;
        this.first = first;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    void setLast(String last) {
        this.last = last;
    }

    public char getInitial() {
        return initial;
    }

    void setInitial(char initial) {
        this.initial = initial;
    }
}
