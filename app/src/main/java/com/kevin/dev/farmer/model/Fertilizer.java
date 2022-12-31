package com.kevin.dev.farmer.model;

import java.io.Serializable;

public class Fertilizer implements Serializable {
    String id,quantity_used,quantity_rem,farmer_id,supplier_id;
    String name;

    public Fertilizer(String id, String quantity_used, String quantity_rem, String farmer_id, String supplier_id, String name) {
        this.id = id;
        this.quantity_used = quantity_used;
        this.quantity_rem = quantity_rem;
        this.farmer_id = farmer_id;
        this.supplier_id = supplier_id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity_used() {
        return quantity_used;
    }

    public void setQuantity_used(String quantity_used) {
        this.quantity_used = quantity_used;
    }

    public String getQuantity_rem() {
        return quantity_rem;
    }

    public void setQuantity_rem(String quantity_rem) {
        this.quantity_rem = quantity_rem;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
