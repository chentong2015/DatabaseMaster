package com.hibernate5.testing.mapping;

public class Event {

    private double id;
    private double underlyingId;
    private int underlyingVersion;
    private String classId;
    private Fees fees;

    public Event() {
    }

    public Event(double underlyingId, int underlyingVersion, String classId, Fees fees) {
        this.underlyingId = underlyingId;
        this.underlyingVersion = underlyingVersion;
        this.classId = classId;
        this.fees = fees;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getUnderlyingId() {
        return underlyingId;
    }

    public void setUnderlyingId(double underlyingId) {
        this.underlyingId = underlyingId;
    }

    public int getUnderlyingVersion() {
        return underlyingVersion;
    }

    public void setUnderlyingVersion(int underlyingVersion) {
        this.underlyingVersion = underlyingVersion;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Fees getFees() {
        return fees;
    }

    public void setFees(Fees fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", underlyingId=" + underlyingId +
                ", underlyingVersion='" + underlyingVersion + '\'' +
                ", classId=" + classId +
                ", fees=" + fees.getCurrency() +
                '}';
    }
}
