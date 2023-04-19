package com.hibernate5.testing.any_master.entities;

public class SnapshotTemplate {

    private double reference;
    private String description;
    private Perspective perspective;

    public SnapshotTemplate() {
    }

    public double getReference() {
        return reference;
    }

    public void setReference(double reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Perspective getPerspective() {
        return perspective;
    }

    public void setPerspective(Perspective perspective) {
        this.perspective = perspective;
    }
}
