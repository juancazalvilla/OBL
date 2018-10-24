package com.apps.wikex.obl;

public class ItemModel {
    private String code;
    private String product;
    private String compareTo;
    private String count;
    private double price;
    private int amount;
    private String imageSrc;

    public ItemModel(String code, String product, String compareTo, String count, double price, String imageSrc) {
        this.code = code;
        this.product = product;
        this.compareTo = compareTo;
        this.count = count;
        this.price = price;
        this.amount = 0;
        this.imageSrc = imageSrc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCompareTo() {
        return compareTo;
    }

    public void setCompareTo(String compareTo) {
        this.compareTo = compareTo;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void changeAmount(int amount) {
        this.amount += amount;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }


    @Override
    public String toString() {
        return code + " - " + product + " " + " X" + amount + "  = " + amount * price ;
    }
}
