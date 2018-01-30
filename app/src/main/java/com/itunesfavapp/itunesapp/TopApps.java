/*
* Homework 06
* TopApps.java
* Hozefa Haveliwala, Nikhil Nagori Group 29
* */

package com.itunesfavapp.itunesapp;


public class TopApps {
    String name, imageUrl, currency;
    float amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        TopApps tA =(TopApps) obj;
        return name.equalsIgnoreCase(tA.name) && amount==tA.amount && currency.equalsIgnoreCase(tA.currency);
    }

    @Override
    public String toString() {
        return name + "\n" +
                "Price: " + currency + " " + amount + "";
    }



}
