package com.hibernate5.testing.testing;

public class Fee {

    private String label;
    private double amount;

    public Fee() {
    }

    public Fee(String label, double amount) {
        this.label = label;
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
