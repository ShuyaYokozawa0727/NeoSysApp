package com.example.myapplication.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SurveyData implements Serializable {
    private ArrayList<Factor> factorList;
    private String surveyMode;
    private int questionItemSizeInMode;

    public SurveyData(ArrayList<Factor> factorList, String surveyMode){
        this.factorList = factorList;
        this.surveyMode = surveyMode;
        if(this.surveyMode.equals("NEOFFI")){
            questionItemSizeInMode = 2;
        }else{
            questionItemSizeInMode = 8;
        }
    }

    public QuestionItem searchQuestionItem(int index){
        return getFactor((index / (questionItemSizeInMode*NEO_NUMS.LOWER_FACTOR_SIZE.getNum())) % NEO_NUMS.FACTOR_SIZE.getNum())
                .getLowerFactor((index / questionItemSizeInMode) % NEO_NUMS.LOWER_FACTOR_SIZE.getNum())
                .getQuestionItem(index % questionItemSizeInMode);
    }

    public Factor getFactor(int index){
        if(index < factorList.size()) return factorList.get(index);
        else return null;
    }

    public ArrayList<Factor> getFactorList(){
        return factorList;
    }

    public void calculateSumScore(){
        // 各下位次元得点,各次元得点を計算してセット
        for(Factor factor : factorList){
            int sumFactorScore = 0;
            for(LowerFactor lowerFactor : factor.getLowerFactorList()){
                int sumLowerFactorScore = 0;
                for(QuestionItem questionItem : lowerFactor.getQuestionItemList()){
                    int answer = questionItem.getAnswer();
                    if(questionItem.getReverseSign().equals("-")){
                        answer = 4 - answer;
                    }
                    sumLowerFactorScore += answer;
                }
                // 合計得点を該当する下位次元の得点として保存
                lowerFactor.setLowerFactorPoint(sumLowerFactorScore);
                sumFactorScore += sumLowerFactorScore;
            }
            // 合計得点を該当する次元の得点として保存
            factor.setFactorPoint(sumFactorScore);
        }
    }
    public void optimizeSurveyData() {
        for(Factor factor : getFactorList()) {
            for (LowerFactor lowerFactor : factor.getLowerFactorList()) {
                lowerFactor.setQuestionItemList(null);
            }
        }
    }

    public String getSurveyMode() {
        return surveyMode;
    }
}
