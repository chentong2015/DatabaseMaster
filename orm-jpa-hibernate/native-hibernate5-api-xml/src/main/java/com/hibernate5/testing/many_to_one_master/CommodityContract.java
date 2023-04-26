package com.hibernate5.testing.many_to_one_master;

public class CommodityContract {

    private double reference;
    private String label;
    private Asset asset;

    public CommodityContract() {
    }

    public CommodityContract(double reference, String label, Asset asset) {
        this.reference = reference;
        this.label = label;
        this.asset = asset;
    }

    public double getReference() {
        return reference;
    }

    public void setReference(double reference) {
        this.reference = reference;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
}
