package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("zip")
    @Expose
    private String zip;

    @SerializedName("shipping")
    @Expose
    private String shipping;

    @SerializedName("condition")
    @Expose
    private String condition;

    @SerializedName("itemId")
    @Expose
    private String itemId;




    public String getName() {
        name = name.toUpperCase();
        if(name.length() > 40)
        {
            name = name.substring(0, 32) + "...";
        }
        else
        {
            if(name.length() < 25)
                name += "\n";
        }
        return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public void setZip(String zip) { this.zip = zip; }
    public String getZip() {
        return "Zip: " + zip;
    }

    public void setShipping(String shipping) { this.shipping = shipping; }
    public String getShipping() {
        return shipping;
    }

    public void setCondition(String condition) { this.condition = condition; }
    public String getCondition() {
        return condition;
    }

    public void setItemId(String condition) { this.itemId = itemId; }
    public String getItemId() {
        return itemId;
    }

}

