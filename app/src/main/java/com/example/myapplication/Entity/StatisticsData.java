package com.example.myapplication.Entity;

public class StatisticsData {
    private String factorSymbol;
    private int lowerFactorSymol;
    private float average;
    private float standardDeviation;

    public StatisticsData(String factorSymbol, int lowerFactorSymol, float average, float standardDeviation){
        this.factorSymbol = factorSymbol;
        this.lowerFactorSymol = lowerFactorSymol;
        this.average = average;
        this.standardDeviation = standardDeviation;
    }

    public String getFactorSymbol() {
        return factorSymbol;
    }

    public int getLowerFactorSymbol() {
        return lowerFactorSymol;
    }

    public float getAverage() {
        return average;
    }

    public float getStandardDeviation() {
        return standardDeviation;
    }
}
