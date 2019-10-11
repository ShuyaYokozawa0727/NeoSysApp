package com.example.myapplication.SurveyUtility;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class ReaderForNEOSystem {
    public static final String SEPARATOR = ",";
    protected BufferedReader bufferedReader;

    public ReaderForNEOSystem(Context context, String filename) {
        try {
            // ファイル読み込み準備
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            this.bufferedReader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                throw new Exception("ファイルオープンできなかった");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
