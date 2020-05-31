package com.aurovarat.kliphez;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Model {
    private String title;
    private String scream;
    private String location;
    private String time;

    public Model() {
    }

    public Model(String title, String scream, String location, String time) {

        this.title = title;
        this.scream = scream;
        this.location = location;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScream() {
        return scream;
    }

    public void setScream(String scream) {
        this.scream = scream;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setImage(String time) {
        this.time = time;
    }
}