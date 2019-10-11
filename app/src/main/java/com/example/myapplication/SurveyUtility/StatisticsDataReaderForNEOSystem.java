package com.example.myapplication.SurveyUtility;

import android.content.Context;

import com.example.myapplication.Entity.StatisticsData;

import java.io.IOException;
import java.util.ArrayList;

public class StatisticsDataReaderForNEOSystem extends ReaderForNEOSystem {

    private String filename;
    // getApplicationContext()
    public StatisticsDataReaderForNEOSystem(Context context, String filename) {
        super(context, filename);
        this.filename = filename;
    }

    public ArrayList<StatisticsData> reader(){
        ArrayList<StatisticsData> statisticsDataList = new ArrayList<>();
        String readLine;
        int lineCount = 0;
        try {
            while ((readLine = bufferedReader.readLine()) != null) {
                lineCount++;
                String splitedData[] = readLine.split(SEPARATOR);
                String factorSymbol = splitedData[0];
                try {
                    int lowerFactorSymbol = Integer.parseInt(splitedData[1]);
                    float average = Float.parseFloat(splitedData[2]);
                    float standardDeviation = Float.parseFloat(splitedData[3]);
                    statisticsDataList.add(new StatisticsData(factorSymbol, lowerFactorSymbol, average, standardDeviation));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("ファイル名 : " + filename + "\n" + lineCount + "行目\n数字が入るべきところに文字列が入っています。");
                }
            }
        } catch (IOException e){
            throw new NullPointerException("ファイル名 : " + filename + "\n" + lineCount + "nullを読み込みました。");
        } finally {
            return statisticsDataList;
        }
    }
}
