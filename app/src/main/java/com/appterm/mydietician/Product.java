package com.appterm.mydietician;

public class Product {
    private String qr;
    private String name;
    private String kcl;
    private String gram;

    public Product(String qr, String name, String kcl, String gram) {
        this.qr = qr;
        this.name = name;
        this.kcl = kcl;
        this.gram = gram;
    }

    public String getGram() {
        return gram;
    }

    public void setGram(String gram) {
        this.gram = gram;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKcl() {
        return kcl;
    }

    public void setKcl(String kcl) {
        this.kcl = kcl;
    }
}
