package com.example.testing.Halpers;

public class Item {
    private int Score;
    private double coordinateN;
    private double coordinateE;

    public Item(int Score, double coordinateN,double coordinateE) {
        this.Score = Score;
        this.coordinateN = coordinateN;
        this.coordinateE = coordinateE;
    }

    public int getScore() {
        return this.Score;
    }

    public double getCoordinateN() {
        return this.coordinateN;
    }

    public double getCoordinateE() {
        return this.coordinateE;
    }


}

