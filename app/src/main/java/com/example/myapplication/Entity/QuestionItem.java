package com.example.myapplication.Entity;

import java.io.Serializable;

public class QuestionItem implements Serializable {
    private int uniqueQuestionNumber;
    private String reverseSign;
    private String questionSentence;
    // Todo このエンティティに回答を追加するか
    private int answer;

    public QuestionItem(int uniqueQuestionNumber, String reverseSign, String questionSentence) {
        this.uniqueQuestionNumber = uniqueQuestionNumber;
        this.reverseSign = reverseSign;
        this.questionSentence = questionSentence;
    }

    public int getQuestionNumber() {
        return uniqueQuestionNumber;
    }

    public String getReverseSign() {
        return reverseSign;
    }

    public String getQuestionSentence() {
        return questionSentence;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getAnswer() { return this.answer; }
}
