package com.example.wvand.drinksdrunk;

import java.io.Serializable;

public class Drink implements Serializable {

    // Attributes for a drink
    private int id;
    private String kind;
    private String timestamp;

    public Drink(int id, String kind, String timestamp) {
        this.id = id;
        this.kind = kind;
        this.timestamp = timestamp;
    }

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

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}