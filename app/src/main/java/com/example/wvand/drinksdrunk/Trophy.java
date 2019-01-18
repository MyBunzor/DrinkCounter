package com.example.wvand.drinksdrunk;

import java.io.Serializable;

// Class that represents a trophy, with a short description as well
public class Trophy implements Serializable {
    private String name, description;
    private int drawableId;
    private boolean achieved;


    public Trophy(String name, String description, int drawableId, boolean achieved) {
        this.name = name;
        this.description = description;
        this.drawableId = drawableId;
        this.achieved = achieved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public boolean getAchieved() { return achieved; }

    public void setAchieved(boolean achieved) {this.achieved = achieved; }

}
