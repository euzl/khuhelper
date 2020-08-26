package com.dasom.khuhelper.admin;

public class AnalyzePlace {
    private int id; // 아이디
    private String key;
    private double finalPoint;
    private double lat;
    private double lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(double finalPoint) {
        this.finalPoint = finalPoint;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}