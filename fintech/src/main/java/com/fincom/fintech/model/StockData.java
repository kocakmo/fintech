package com.fincom.fintech.model;

public class StockData {
    private String ticker;
    private double price;
    private double changeAmount;

    private double changePercentage;
    private int volume;

    public StockData() {
    }

    public StockData(String ticker, double price, double changeAmount, double changePercentage, int volume) {
        this.ticker = ticker;
        this.price = price;
        this.changeAmount = changeAmount;
        this.changePercentage = changePercentage;
        this.volume = volume;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}

