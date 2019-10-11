package com.example.myapplication.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.Entity.Factor;
import com.example.myapplication.Entity.LowerFactor;
import com.example.myapplication.Entity.StatisticsData;
import com.example.myapplication.Entity.SurveyData;
import com.example.myapplication.R;
import com.example.myapplication.SurveyUtility.StatisticsDataReaderForNEOSystem;

import java.util.ArrayList;

public class ResultPlatformActivity extends FullScreenActivity {

    private Button buttonToTable;
    private Button buttonToGraph;
    private Button buttonToSummary;

    private SurveyData surveyData;
    private int age;
    private String gender;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_platform);

        this.surveyData = (SurveyData) getIntent().getSerializableExtra("SURVEY_DATA");
        this.age = getIntent().getIntExtra("AGE", 20);
        this.gender = getIntent().getStringExtra("GENDER");
        this.mode = getIntent().getStringExtra("MODE");

        String readFile = generateFileName();

        ArrayList<StatisticsData> statisticsDataList = null;
        try {
            statisticsDataList = new StatisticsDataReaderForNEOSystem(getApplicationContext(), readFile).reader();
        } catch (NullPointerException e) {
            // 正常終了
        } catch (NumberFormatException e) {
            showDialogConvertType(e.getMessage());
        } catch (Exception e) {
            showForbiddenFile(e.getMessage());
        }
        // 各次元のTScoreをセット
        setTScoreFactors(statisticsDataList);

        buttonToGraph = findViewById(R.id.buttonToGraph);
        buttonToTable =  findViewById(R.id.buttonToTable);
        buttonToSummary = findViewById(R.id.buttonToSummary);

        buttonToGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // データを次のActivityに受け渡す
                Intent intentNext = new Intent(getApplicationContext(), ResultGraphActivity.class);
                startNextActivity(intentNext);
            }
        });

        buttonToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // データを次のActivityに受け渡す
                Intent intentNext = new Intent(getApplicationContext(), ResultTableActivity.class);
                startNextActivity(intentNext);
            }
        });

        buttonToSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // データを次のActivityに受け渡す
                Intent intentNext = new Intent(getApplicationContext(), ResultSummaryActivity.class);
                startNextActivity(intentNext);
            }
        });
    }

    @Override
    public void onBackPressed(){
        // 行いたい処理
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("この画面でBackはできません")
                .setMessage("OKでダイアログを閉じる")
                .setNegativeButton("情報入力画面へ戻る", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }
    private void startNextActivity(Intent intentNext){
        // 回答情報を格納したエンティティ
        intentNext.putExtra("SURVEY_DATA", surveyData);
        intentNext.putExtra("MODE", mode);
        // 次のアクティビティを発射
        startActivity(intentNext);
    }

    private String generateFileName() {
        String filename = "StatisticData/" + surveyData.getSurveyMode() + "/";
        filename += surveyData.getSurveyMode() + "_";
        // TODO : 大学生以下の年齢の場合、値を累積して平均と標準偏差テーブルを作成する。
        if((0 <= age) && (age < 12)) {
            filename += "Child" + "_";
        } else if((12 <= age) && age < 15) {
            filename += "JuniorHighSchool" + "_";
        } else if((15 <= age) && age < 18) {
            filename += "HighSchool" + "_";
        } else if((18 <= age) && (age <= 22)) {
            // TODO : 大学生以上の場合得られたデータで増強した新しい平均・標準偏差テーブルを作成する
            filename += "University" + "_";
        } else {
            filename += "Adalt" + "_";
        }
        switch(gender) {
            case "男性" : filename += "MAN"; break;
            case "女性" : filename += "WOMAN"; break;
            default : filename += "ENTIRETY";
        }
        filename += ".csv";
        return filename;
    }

    private void setTScoreFactors(ArrayList<StatisticsData> statisticsDataList){
        int factorCounter = 0;
        int lowerFactorCounter = 0;
        for(StatisticsData statisticsData : statisticsDataList){
            float average = statisticsData.getAverage();
            float standardDeviation = statisticsData.getStandardDeviation();
            // TScoreの統計データ
            if(statisticsData.getLowerFactorSymbol() == 0){
                // FactorのTScoreを計算して保存
                Factor factor = surveyData.getFactor(factorCounter);
                float tScore= calculateTScore(factor.getFactorPoint(),average,standardDeviation);
                factor.setFactorTScore(tScore);
                factorCounter++;
            } else{
                // LowerFactorのTScoreを計算して保存
                // factorは6回に1回値が変わる lowerFactorは6回に1回ゼロに戻る
                LowerFactor lowerFactor = surveyData.getFactor(lowerFactorCounter/6).getLowerFactor(lowerFactorCounter%6);
                lowerFactor.setLowerFactorTScore(calculateTScore(lowerFactor.getLowerFactorPoint(), average, standardDeviation));
                lowerFactorCounter++;
            }
        }
    }

    private float calculateTScore(int score, float average, float standardDeviation) {
        return  ((score-average) / standardDeviation * 10 + 50);
    }

    private void showForbiddenFile(String errorMessage){
        AlertDialog attention = new AlertDialog.Builder(this)
                .setTitle("ファイルが存在しません")
                .setMessage(errorMessage)
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }

    private void showDialogConvertType(String errorMessage){
        AlertDialog attention = new AlertDialog.Builder(this)
                .setTitle("型変換エラー")
                .setMessage(errorMessage)
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }
}
