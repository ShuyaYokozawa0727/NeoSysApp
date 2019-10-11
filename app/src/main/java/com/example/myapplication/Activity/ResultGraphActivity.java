package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Entity.Factor;
import com.example.myapplication.Entity.LowerFactor;
import com.example.myapplication.Entity.SurveyData;
import com.example.myapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ResultGraphActivity extends FullScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_graph);

        createLineData();
    }

    private void createLineData() {
        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
        lineChart.setDescription("グラフ上やラベルをタッチすると黄色のサポートラインが表示されます。");

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(true);

        lineChart.setDrawGridBackground(true);
        lineChart.setEnabled(true);

        // タッチでズームできる
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(true);

        // ??
        lineChart.setScaleEnabled(true);
        lineChart.getLegend().setEnabled(true);

        //X軸周り
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setSpaceBetweenLabels(0);

        lineChart.setData(createLineDataData());

        lineChart.invalidate();
        // アニメーション
        lineChart.animateY(2000, Easing.EasingOption.EaseInBack);
    }

    // LineDataの設定
    private LineData createLineDataData() {

        Intent intent = getIntent();

        SurveyData surveyData = (SurveyData) intent.getSerializableExtra("SURVEY_DATA");
        String mode = intent.getStringExtra("MODE");

        // 完成データ
        ArrayList<LineDataSet> completedDataSets = new ArrayList<>();
        LineData lineData = null;

        // Factor
        int throughCounter = 0;
        ArrayList<String> Labels = new ArrayList<>();
        ArrayList<Entry> valuesFactor = new ArrayList<>();
        // グラフのデータをセット
        for (Factor factor : surveyData.getFactorList()) {
            String splited[] = factor.getFactorName().split("[:]");
            Labels.add(splited[0]);
            valuesFactor.add(new Entry(factor.getFactorTScore(), throughCounter));
            throughCounter++;
        }
        // データとそれに関連づくラベルをセット
        LineDataSet valuesFactorDataSet = new LineDataSet(valuesFactor, "Factors");
        valuesFactorDataSet.setColor(ColorTemplate.PASTEL_COLORS[0]);
        completedDataSets.add(valuesFactorDataSet);

        // LowerFactor
        if (mode.equals("NEOPI")) {
            int colorCount = 0;
            for (Factor factor : surveyData.getFactorList()) {
                String splited[] = null;
                ArrayList<Entry> valuesLower = new ArrayList<>();
                // グラフのデータをセット
                for (LowerFactor lowerFactor : factor.getLowerFactorList()){
                    splited = lowerFactor.getLowerFactorName().split("[:]");
                    Labels.add(splited[0]);
                    valuesLower.add(new Entry(lowerFactor.getLowerFactorTScore(), throughCounter));
                    throughCounter++;
                }
                LineDataSet valuesLowerDataSet = new LineDataSet(valuesLower, "LowerFactor" + factor.getFactorName().split("[:]")[0]);
                valuesLowerDataSet.setColor(ColorTemplate.COLORFUL_COLORS[(colorCount % 5)]);
                completedDataSets.add(valuesLowerDataSet);
                colorCount++;
            }
        }
        lineData = new LineData(Labels, completedDataSets);
        return lineData;
    }
}
