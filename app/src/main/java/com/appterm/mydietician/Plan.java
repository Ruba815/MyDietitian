package com.appterm.mydietician;

import java.util.ArrayList;

public class Plan {
    private ArrayList<Food> breakfast = new ArrayList<>();

    private ArrayList<Food> lunch = new ArrayList<>();
    private ArrayList<Food> dinner = new ArrayList<>();

    public ArrayList<Food> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ArrayList<Food> breakfast) {
        this.breakfast = breakfast;
    }

    public ArrayList<Food> getLunch() {
        return lunch;
    }

    public void setLunch(ArrayList<Food> lunch) {
        this.lunch = lunch;
    }

    public ArrayList<Food> getDinner() {
        return dinner;
    }

    public void setDinner(ArrayList<Food> dinner) {
        this.dinner = dinner;
    }
}
