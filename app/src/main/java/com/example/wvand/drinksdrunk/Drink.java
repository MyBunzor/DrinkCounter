package com.example.wvand.drinksdrunk;

import java.io.Serializable;

public class Drink implements Serializable {

    // Attributes for drink class
    private int id;
    private String kind;
    private String timestamp;

    public Drink(String kind, String timestamp) {
        this.kind = kind;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public String getTimestamp() {
        return timestamp;
    }

}