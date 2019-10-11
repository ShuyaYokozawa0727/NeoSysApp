package com.example.myapplication.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Factor implements Serializable {
    private String factorName;
    private int factorPoint = 0;
    private float factorTScore = 0;
    private ArrayList<LowerFactor> lowerFactorList;

    public Factor(String factorName, ArrayList<LowerFactor> lowerFactorList) {
        this.factorName = factorName;
        this.lowerFactorList = lowerFactorList;
    }

    public String getFactorName(){
        return this.factorName;
    }

    public int getFactorPoint() { return this.factorPoint; }

    public void setFactorPoint(int factorPoint){
        this.factorPoint = factorPoint;
    }

    public float getFactorTScore() { return factorTScore; }

    public void setFactorTScore(float factorTScore) { this.factorTScore = factorTScore; }

    public LowerFactor getLowerFactor(int index) {
        if(index < lowerFactorList.size()){
            return this.lowerFactorList.get(index);
        }else{
            return null;
        }
    }
    public ArrayList<LowerFactor> getLowerFactorList(){
        return lowerFactorList;
    }
}
