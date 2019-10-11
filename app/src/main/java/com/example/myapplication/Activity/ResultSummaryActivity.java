package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Entity.Factor;
import com.example.myapplication.Entity.SurveyData;
import com.example.myapplication.R;

public class ResultSummaryActivity extends FullScreenActivity {

    private SurveyData surveyData;

    private TextView textViewSummaryN;
    private TextView textViewSummaryE;
    private TextView textViewSummaryO;
    private TextView textViewSummaryA;
    private TextView textViewSummaryC;
    private TextView textViewSummaryNTitle;
    private TextView textViewSummaryETitle;
    private TextView textViewSummaryOTitle;
    private TextView textViewSummaryATitle;
    private TextView textViewSummaryCTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_summary);

        textViewSummaryN = findViewById(R.id.textViewSummaryN);
        textViewSummaryE = findViewById(R.id.textViewSummaryE);
        textViewSummaryO = findViewById(R.id.textViewSummaryO);
        textViewSummaryA = findViewById(R.id.textViewSummaryA);
        textViewSummaryC = findViewById(R.id.textViewSummaryC);
        textViewSummaryNTitle = findViewById(R.id.textViewSummaryNTitle);
        textViewSummaryETitle = findViewById(R.id.textViewSummaryETitle);
        textViewSummaryOTitle = findViewById(R.id.textViewSummaryOTitle);
        textViewSummaryATitle = findViewById(R.id.textViewSummaryATitle);
        textViewSummaryCTitle = findViewById(R.id.textViewSummaryCTitle);

        Intent intent = getIntent();
        surveyData = (SurveyData) intent.getSerializableExtra("SURVEY_DATA");

        setSummaryText();

    }

    private void setSummaryText() {
        for(Factor factor : surveyData.getFactorList()) {
            switchSummary(factor.getFactorName(), factor.getFactorTScore());
        }
    }

    private void switchSummary(String factorName, float tScore) {
        if(factorName.equals("N:神経症傾向(Neuroticism)")) {
            textViewSummaryNTitle.setText(factorName);
            if(tScore < 44) {
                textViewSummaryN.setText("安定していて、しっかりしており、ストレスのかかる状況でも落ち着いている。");
            } else if(56 < tScore) {
                textViewSummaryN.setText("感受性が強く、感情的で、取り乱しやすい");
            } else {
                textViewSummaryN.setText("普段は落ち着いており、ストレスに対処できるが、時折、罪悪感や怒り、悲しみを経験する。");
            }
        } else if(factorName.equals("E:外向性(Extraversion)")) {
            textViewSummaryETitle.setText(factorName);
            if(tScore < 44) {
                textViewSummaryE.setText("内向的で控えめでまじめである。1人または2~3人の親しい人と一緒にいるのを好む。");
            } else if(56 < tScore) {
                textViewSummaryE.setText("社交的、外交的、活動的で元気はつらつとしている。いつも誰かが周りにいることを好む。");
            } else {
                textViewSummaryE.setText("活動性や物事に対する熱中度はほどほどである。仲間との付き合いを楽しむ一方で、プライベートも尊重している。");
            }
        } else if(factorName.equals("O:開放性(Openness)")) {
            textViewSummaryOTitle.setText(factorName);
            if(tScore < 44) {
                textViewSummaryO.setText("現実的、実際的で伝統に従い、自分のやり方がほとんど決まっている。");
            } else if(56 < tScore) {
                textViewSummaryO.setText("様々な経験に対して前向きに取り組み、興味が広く、想像力が豊かである。");
            } else {
                textViewSummaryO.setText("現実的であるが、新しいやり方を考えるのも嫌いではない。新しいことと古いことのバランスを取ろうとする。");
            }
        } else if(factorName.equals("A:調和性(Agreeableness)")) {
            textViewSummaryATitle.setText(factorName);
            if(tScore < 44) {
                textViewSummaryA.setText("頑固で融通がきかず、疑い深く、うぬぼれ屋で、競争的である。怒りをストレートに表す傾向がある。");
            } else if(56 < tScore) {
                textViewSummaryA.setText("思いやりがあり、濃厚で、人と協力することを望み、衝突を避ける。");
            } else {
                textViewSummaryA.setText("普段は温かく信頼感があり、愛想がよいが、時折、頑固になったり競争的になったりする。");
            }
        } else if(factorName.equals("C:誠実性(Conscientiousness)")) {
            textViewSummaryCTitle.setText(factorName);
            if(tScore < 44) {
                textViewSummaryC.setText("のんきで、だらしないところがあり、時々、軽はずみなことをする。計画を立てるのは好きではない。");
            } else if(56 < tScore) {
                textViewSummaryC.setText("誠実で、何事に対してもきちんとしている。目標が高く、いつも目標を達成するために努力をしている。");
            } else {
                textViewSummaryC.setText("頼りになり、ほどほどにきちんとしている。たいていは明確な目標を持っているが、時にはそれを後回しにすることもある。");
            }
        } else {
            textViewSummaryNTitle.setText(factorName);
        }
    }
}