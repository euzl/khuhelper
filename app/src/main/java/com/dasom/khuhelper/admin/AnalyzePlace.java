package com.dasom.khuhelper.admin;

import android.graphics.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnalyzePlace {
    private int id; // 아이디
    private double finalPoint; // 최종포인트(높을수록 추천)
    private ArrayList<Float> latitudes; // 위도
    private ArrayList<Float> longtitudes; // 경도
    private double[][] points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFinalPoint() {
        return finalPoint;
    }

    public void setFinalPoint(double finalPoint) {
        this.finalPoint = finalPoint;
    }

    public ArrayList<Float> getlatitudes() {
        return latitudes;
    }

    public void setlatitudes(ArrayList<Float> latitudes) {
        this.latitudes = latitudes;
    }

    public ArrayList<Float> getlongtitudes() {
        return longtitudes;
    }

    public void setlongtitudes(ArrayList<Float> longtitudes) {
        this.longtitudes = longtitudes;
    }

    public double[][] getPoints() {
        return points;
    }

    public void setPoints(double[][] points) {
        this.points = points;
    }
}