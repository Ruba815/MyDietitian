package com.appterm.mydietician;

import java.util.ArrayList;

public class ClientInformation{

    private String age;
    private String loseORgain;
    private String height;
    private String weight;
    private String number;

    private ArrayList<String> vegetables;
    private ArrayList<String> fruits;
    private ArrayList<String> starches;
    private ArrayList<String> meat;
    private ArrayList<String> dairy;

    public ArrayList<String> getVegetables() {
        return vegetables;
    }

    public void setVegetables(ArrayList<String> vegetables) {
        this.vegetables = vegetables;
    }

    public ArrayList<String> getFruits() {
        return fruits;
    }

    public void setFruits(ArrayList<String> fruits) {
        this.fruits = fruits;
    }

    public ArrayList<String> getStarches() {
        return starches;
    }

    public void setStarches(ArrayList<String> starches) {
        this.starches = starches;
    }

    public ArrayList<String> getMeat() {
        return meat;
    }

    public void setMeat(ArrayList<String> meat) {
        this.meat = meat;
    }

    public ArrayList<String> getDairy() {
        return dairy;
    }

    public void setDairy(ArrayList<String> dairy) {
        this.dairy = dairy;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setLoseORgain(String loseORgain) {
        this.loseORgain = loseORgain;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public String getLoseORgain() {
        return loseORgain;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getNumber() {
        return number;
    }
}
