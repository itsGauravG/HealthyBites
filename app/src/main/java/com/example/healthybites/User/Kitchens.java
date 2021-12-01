package com.example.healthybites.User;

import android.widget.ImageView;

public class Kitchens {
    public String getKitchen_name() {
        return kitchen_name;
    }

    public void setKitchen_name(String kitchen_name) {
        this.kitchen_name = kitchen_name;
    }

    public String getKitchen_address() {
        return kitchen_address;
    }

    public void setKitchen_address(String kitchen_address) {
        this.kitchen_address = kitchen_address;
    }

    public ImageView getKitchen_image() {
        return kitchen_image;
    }

    public void setKitchen_image(ImageView kitchen_image) {
        this.kitchen_image = kitchen_image;
    }

    String kitchen_name , kitchen_address;
    ImageView kitchen_image;


}
