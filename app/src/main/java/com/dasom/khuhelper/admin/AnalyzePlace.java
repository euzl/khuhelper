package com.dasom.khuhelper.admin;

public class AnalyzePlace {
    private int id; // 아이디
    private double finalPoint; // 최종포인트(높을수록 추천)
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

    public double[][] getPoints() {
        return points;
    }

    public void setPoints(double[][] points) {
        this.points = points;
    }
}