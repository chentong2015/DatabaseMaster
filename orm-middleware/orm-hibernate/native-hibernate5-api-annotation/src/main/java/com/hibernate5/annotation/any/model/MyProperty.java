package com.hibernate5.annotation.any.model;

public interface MyProperty<T> {

    String getName();

    T getValue();
}
