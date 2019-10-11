package com.example.myapplication.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class LowerFactor implements Serializable {
    private String lowerFactorName;
    private int lowerFactorPoint = 0;
    private float lowerFactorTScore = 0;
    private ArrayList<QuestionItem> questionItemList;

    // コンストラクタ
    public LowerFactor(String lowerFactorName, ArrayList<QuestionItem> questionItems) {
        this.lowerFactorName = lowerFactorName;
        this.questionItemList = questionItems;
    }

    public String getLowerFactorName() {
        return this.lowerFactorName;
    }

    public int getLowerFactorPoint(){
        return this.lowerFactorPoint;
    }

    public void setLowerFactorPoint(int lowerFactorPoint){
        this.lowerFactorPoint = lowerFactorPoint;
    }

    public void setLowerFactorTScore(float lowerFactorTScore) {
        this.lowerFactorTScore = lowerFactorTScore;
    }

    public float getLowerFactorTScore() {
        return lowerFactorTScore;
    }

    public QuestionItem getQuestionItem(int index) {
        if(index < questionItemList.size()){
            return this.questionItemList.get(index);
        }else{
            return null;
        }
    }
    public ArrayList<QuestionItem> getQuestionItemList(){
        return questionItemList;
    }

    public void setQuestionItemList(ArrayList<QuestionItem> questionItemList) {
        this.questionItemList = questionItemList;
    }
}
