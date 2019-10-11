package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Entity.Factor;
import com.example.myapplication.Entity.LowerFactor;
import com.example.myapplication.Entity.NEO_NUMS;
import com.example.myapplication.Entity.QuestionItem;
import com.example.myapplication.Entity.SurveyData;
import com.example.myapplication.Fragment.SurveyFragment;
import com.example.myapplication.R;
import com.example.myapplication.SurveyUtility.QuestionReaderForNEOSystem;

import java.util.ArrayList;
import java.util.Collections;

public class SurveyActivity extends FullScreenActivity implements SurveyFragment.OnFragmentInteractionListener {

    private Button buttonSave;
    private ProgressBar progressBarSurvey;
    private ScrollView scrollViewSurvey;
    //
    private int pickupCount = 0;
    //
    private int questionItemSizeInMode;
    private int questionFullSizeInMode;

    private String surveyMode = "";
    private String debugMode = "";
    private String filename = "NEOPI_Question.csv";
    private SurveyData surveyData = null;

    private ArrayList<Integer> factorRandomAccessList = new ArrayList<>();
    private ArrayList<Integer> lowerFactorRandomAccessList = new ArrayList<>();
    private ArrayList<Integer> questionItemRandomAccessList = new ArrayList<>();

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        this.intent = this.getIntent();

        buttonSave = findViewById(R.id.buttonSave);
        progressBarSurvey = findViewById(R.id.progressBarSurvey);
        scrollViewSurvey = findViewById(R.id.scrollViewSurvey);

        this.surveyMode = intent.getStringExtra("MODE");
        this.debugMode = intent.getStringExtra("DEBUG");

        questionFullSizeInMode = NEO_NUMS.NEOPI_QUESTION_SIZE.getNum();
        questionItemSizeInMode = NEO_NUMS.NEOPI_ITEM_SIZE.getNum();

        if(surveyMode.equals("NEOFFI")){
            questionFullSizeInMode = NEO_NUMS.NEOFFI_QUESTION_SIZE.getNum();
            questionItemSizeInMode = NEO_NUMS.NEOFFI_ITEM_SIZE.getNum();
        }

        progressBarSurvey.setMax(questionFullSizeInMode);

        try {
            surveyData = new QuestionReaderForNEOSystem(getApplicationContext(), filename, surveyMode).reader();
        }catch (Exception e) {
            showAlertDialog("データに不具合があります。","ファイル名 : " + filename + "\n Cause : " + e.getCause() + "\n Message : " + e.getMessage());
        }

        // ランダムアクセス用のリスト一覧を作成

        createRandomAccessLists();
        if(debugMode.equals("有効")){
            for(int index = 0; index < questionFullSizeInMode; index++){
                commitQuestionFragment();
            }
        } else {
            // 最初のコミット
            commitQuestionFragment();
        }
        buttonSave.setOnClickListener(new View.OnClickListener() {
            // 測定ボタンを押されたら
            @Override
            public void onClick(View view) {
                finishedSurvey();
                startNextActivity();
            }
        });
    }

    @Override
    public void onFragmentInteraction(int mPickupCount){
        if(mPickupCount == pickupCount-1){
            if(pickupCount < questionFullSizeInMode){
                // 次のフラグメントを発射
                commitQuestionFragment();
            } else {
                buttonSave.setVisibility(View.VISIBLE);
            }
        }
    }

    // ランダムなFactorのランダムなLoewFactorのランダムなQuestionItemに重複なしでアクセスする素材リストの作成
    private void createRandomAccessLists(){
        for(int factorIndex = 0; factorIndex < NEO_NUMS.FACTOR_SIZE.getNum(); factorIndex++){
            factorRandomAccessList.add(factorIndex);
        }
        for(int lowerFactorIndex = 0; lowerFactorIndex < NEO_NUMS.LOWER_FACTOR_SIZE.getNum(); lowerFactorIndex++){
            lowerFactorRandomAccessList.add(lowerFactorIndex);
        }
        for (int questionItemIndex = 0; questionItemIndex < questionItemSizeInMode; questionItemIndex++){
            questionItemRandomAccessList.add(questionItemIndex);
        }
        if(debugMode.equals("有効")){
            // 順番どおりに表示するのでシャッフルしない
        } else {
            Collections.shuffle(factorRandomAccessList);
            Collections.shuffle(lowerFactorRandomAccessList);
            Collections.shuffle(questionItemRandomAccessList);
        }
    }

    // 重複しないランダムなQuestionItemをFragmentに格納してcommitする。
    public void commitQuestionFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int cycleFactor;
        int cycleLowerFactor;
        int cycleQuestionItem;

        if(debugMode.equals("有効")){
            // 順番にアクセス
            // pickupCountが12 or 48の倍数になるたびに1増える
            cycleFactor = pickupCount / (questionItemSizeInMode * NEO_NUMS.LOWER_FACTOR_SIZE.getNum());
            // pickupCountが2,8の倍数になるたびに1増え、pickupCountが12,48の倍数になると0に戻る
            cycleLowerFactor = (pickupCount / questionItemSizeInMode) % NEO_NUMS.LOWER_FACTOR_SIZE.getNum();
            // pickupCountが2,8の倍数になると0になる。
            cycleQuestionItem = pickupCount % questionItemSizeInMode;
        } else {
            // ランダム
            // pickupCountが5の倍数になるたびに0に戻る
            cycleFactor = pickupCount % NEO_NUMS.FACTOR_SIZE.getNum();
            // pickupCountが6の倍数になるたびに0に戻る
            cycleLowerFactor = pickupCount % NEO_NUMS.LOWER_FACTOR_SIZE.getNum();
            // pickupCountが 30の倍数になると次のItemへ進む
            cycleQuestionItem = pickupCount / (NEO_NUMS.FACTOR_SIZE.getNum() * NEO_NUMS.LOWER_FACTOR_SIZE.getNum());
        }

        Factor factor = surveyData.getFactor(factorRandomAccessList.get(cycleFactor));
        LowerFactor lowerFactor = factor.getLowerFactor(lowerFactorRandomAccessList.get(cycleLowerFactor));
        QuestionItem questionItem = lowerFactor.getQuestionItem(questionItemRandomAccessList.get(cycleQuestionItem));

        transaction.add(R.id.container, SurveyFragment.newInstance(pickupCount, questionItem, lowerFactor.getLowerFactorName(), debugMode),"Q"+pickupCount);
        transaction.commit();
        pickupCount++;
        scrollToBottom();
        progressBarSurvey.setProgress(pickupCount);
    }

    private void scrollToBottom(){
        scrollViewSurvey.post(new Runnable() {
            @Override
            public void run() {
                scrollViewSurvey.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    private void finishedSurvey() {
        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        // すべてのフラグメントのシークバーの値を、それに紐づくQuestionインスタンスに格納する
        for (int index = 0; index < questionFullSizeInMode; index++) {
            SurveyFragment getFragment = (SurveyFragment) fragmentManager.findFragmentByTag("Q" + index);
            // 該当するタグのfragment検出
            if (getFragment != null) {
                if (getFragment.getCheckedRadioButton().equals("未選択")) {
                    showAlertDialog("未回答の質問があります", "Q" + (index + 1));
                } else {
                    // 質問固有番号を取得
                    int questionUniqueNumber = surveyData.searchQuestionItem(index).getQuestionNumber();
                    // 回答を数値化してsurveyDataに保存
                    // TODO: 正しい位置に保存されているか
                    surveyData.searchQuestionItem(questionUniqueNumber).setAnswer(convertAnswer(getFragment.getCheckedRadioButton()));
                }
            }
        }
    }

    private int convertAnswer(String checkedLabel){
        int checkedValue = -10000;
        if (checkedLabel.equals(getResources().getString(R.string.super_no))) {
            checkedValue = 0;
        } else if (checkedLabel.equals(getResources().getString(R.string.no))) {
            checkedValue = 1;
        } else if (checkedLabel.equals(getResources().getString(R.string.even))) {
            checkedValue = 2;
        } else if (checkedLabel.equals(getResources().getString(R.string.yes))) {
            checkedValue = 3;
        } else if (checkedLabel.equals(getResources().getString(R.string.super_yes))) {
            checkedValue = 4;
        }
        return checkedValue;
    }

    private void startNextActivity(){
        // データを次のActivityに受け渡す
        Intent intentNext = new Intent(getApplicationContext(), ResultPlatformActivity.class);

        // 回答情報を格納したエンティティ
        // QuestionItemをすべて消去して次のアクティビティに渡す
        // (intentは1MBまでしかputできない)

        surveyData.calculateSumScore();
        surveyData.optimizeSurveyData();
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentNext.putExtra("SURVEY_DATA", surveyData);
        intentNext.putExtra("MODE", intent.getStringExtra("MODE"));
        intentNext.putExtra("AGE", intent.getStringExtra("AGE"));
        intentNext.putExtra("GENDER", intent.getStringExtra("GENDER"));

        startActivity(intentNext);
        // バインダトランザクションバッファの制限サイズは現在1Mbで、プロセスで進行中のすべてのトランザクションで共有されます。
        // したがって、個々のトランザクションの大部分が中程度のサイズであっても、進行中のトランザクションが多数ある場合、この例外がスローされます。
        finish(); // このアクティビティを破棄する
    }

    public void showAlertDialog(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }
}
