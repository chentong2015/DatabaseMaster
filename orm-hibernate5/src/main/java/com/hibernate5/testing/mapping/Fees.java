package com.hibernate5.testing.mapping;

import java.util.HashSet;
import java.util.Set;

public class Fees {

    private double id;
    private String currency;
    private Set<Fee> fees = new HashSet<>();

    public Fees() {
    }

    public Fees(String currency, Set<Fee> fees) {
        this.currency = currency;
        this.fees = fees;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<Fee> getFees() {
        return fees;
    }

    public void setFees(Set<Fee> fees) {
        this.fees = fees;
    }
}
