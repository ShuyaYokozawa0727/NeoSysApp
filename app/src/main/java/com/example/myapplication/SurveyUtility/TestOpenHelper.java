package com.example.myapplication.SurveyUtility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class TestOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TestDB.db";
    private static final String TABLE_NAME = "testdb";

    private static final int DATABASE_VERSION = 3;
    private static final String _ID = "_id";
    private static final String COLUMN_NAME_TITLE = "title";
    private static final String COLUMN_NAME_SUBTITLE = "score";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "("
                    + _ID + " INTEGER PRIMARY KEY," //  PRIMARY KEY :  既存のIDと同一のIDを持つデータは保存できないようにする(複数のカラムに設定した場合、どちらのカラムの値もかぶると保存できなくなる)
                    + COLUMN_NAME_TITLE + "TEXT,"
                    + COLUMN_NAME_SUBTITLE + "INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public TestOpenHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // execSQLはSQL分を実行する
        db.execSQL(
                SQL_CREATE_ENTRIES
        );

        saveData(db, "music1", 10);
        saveData(db, "music2", 0);
        saveData(db, "music3", 0);
        saveData(db, "music4", 0);
        saveData(db, "music5", 0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onCreate()でテーブルを作成し、DBのバージョン（DATABASE_VERSION）をあげると
        // 古いテーブルを削除し、新しいテーブルを作成する
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    // saveDataメソッドはカラムのタイトルとカラムに保存したい値を設定して、保存したいDBに保存する
    public void saveData(SQLiteDatabase db, String title, int score) {
        // テーブルのカラムをキーとして、保存したい値をペアで保存するためのクラス
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("score", score);

        db.insert("testdb", null, values);
    }

    public void readAllData(SQLiteDatabase db, View view) {
        Cursor cursor = db.query(
                "testdb",
                new String[] {"title, score"},
                null,
                null,
                null,
                null,
                null
        );
    }
}
