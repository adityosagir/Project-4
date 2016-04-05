package com.example.asagir.spotacart;

import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by generalassembly on 3/30/16.
 */
public class FoodCart implements Serializable {

    private String name;
    private String address;
    private String description;
    private double latitude;
    private double longitude;
    private String imageURL;

    public FoodCart(String name, String address, String description, double latitude, double longitude, String imageURL) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageURL = imageURL;
    }

    public FoodCart() {
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
