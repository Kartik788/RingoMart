package com.ringolatechapps.ringomart;

public class CategoryClass {

    int img_url;
    String text;

    public CategoryClass(int img_url, String text) {
        this.img_url=img_url;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getImg_url() {
        return img_url;
    }
}
