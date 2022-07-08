package com.ringolatechapps.ringomart;

public class PRODUCT_CLASS {
    String UUID = null;
    String ImgUrl;
    String Name;
    String Price;
    String Rating;

    int num_of_items;
    int coupons;
    double price_details;
    double total_discount;
    double delivery_charges;
    double total_amount;


    public PRODUCT_CLASS(String UUID, String imgUrl, String name, String price) {
        this.UUID = UUID;
        ImgUrl = imgUrl;
        Name = name;
        Price = price;
    }

    public PRODUCT_CLASS(String UUID, String imgUrl, String name, String price, String rating) {
        this.UUID = UUID;
        ImgUrl = imgUrl;
        Name = name;
        Price = price;
        Rating = rating;
    }


    public PRODUCT_CLASS(int num_of_items, int coupons,double price_details, double total_discount, double delivery_charges, double total_amount) {
        this.num_of_items = num_of_items;
        this.coupons = coupons;
        this.price_details = price_details;
        this.total_discount = total_discount;
        this.delivery_charges = delivery_charges;
        this.total_amount = total_amount;
    }


    public String getUUID() {
        return UUID;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getName() {
        return Name;
    }

    public String getRating() {
        return Rating;
    }

    public String getPrice() {
        return Price;
    }

    public int getNum_of_items() {
        return num_of_items;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public double getDelivery_charges() {
        return delivery_charges;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public int getCoupons() {
        return coupons;
    }

    public double getPrice_details() {
        return price_details;
    }
}
