package com.example.myapplication;

public class SimilarItemData {
    String imageUrl;
    String title;
    String shipping;
    String price;
    String daysleft;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        if(shipping.equals("0"))
            shipping = "Free Shipping";
        else
            shipping = "$"+shipping;
        this.shipping = shipping;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        price = "$"+price;
        this.price = price;
    }

    public String getDaysleft() {
        return daysleft;
    }

    public void setDaysLeft(String daysleft) {
        this.daysleft = daysleft;
    }
}
