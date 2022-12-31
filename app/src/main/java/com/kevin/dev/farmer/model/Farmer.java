package com.kevin.dev.farmer.model;

import com.google.gson.Gson;

public class Farmer {
    String  id;
    String phone;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public Farmer(String id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
