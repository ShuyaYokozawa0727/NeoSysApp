package com.example.myapplication.Activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Entity.Factor;
import com.example.myapplication.Entity.LowerFactor;
import com.example.myapplication.Entity.SurveyData;
import com.example.myapplication.Fragment.ResultTableFragment;
import com.example.myapplication.R;

public class ResultTableActivity extends FullScreenActivity {

    private Button buttonResult;

    private SurveyData surveyData;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_table);

        this.surveyData = (SurveyData) getIntent().getSerializableExtra("SURVEY_DATA");
        this.mode = getIntent().getStringExtra("MODE");
        commitAnalysisFragment();

    }
    private void commitAnalysisFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int countFactor = 0;
        // FACTORを発射
        for(Factor factor : surveyData.getFactorList()) {
            // タイトルとTScoreの結果を入れて発射
            transaction.add(R.id.container, ResultTableFragment.newInstance(factor.getFactorName(), factor.getFactorTScore()),"Factor"+countFactor);
            countFactor++;
        }

        // NEOPIモードならば
        if (mode.equals("NEOPI")){
            int countLowerFactor = 0;
            for(Factor factor : surveyData.getFactorList()) {
                // LOWERFACTORを発射
                for(LowerFactor lowerFactor : factor.getLowerFactorList()) {
                    transaction.add(R.id.container, ResultTableFragment.newInstance(lowerFactor.getLowerFactorName(), lowerFactor.getLowerFactorTScore()),"LowerFactor"+countLowerFactor);
                    countLowerFactor++;
                }
            }
        }
        transaction.commit();
    }
}
