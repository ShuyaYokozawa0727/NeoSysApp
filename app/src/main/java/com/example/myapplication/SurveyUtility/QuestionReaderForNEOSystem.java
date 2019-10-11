package com.example.myapplication.SurveyUtility;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.Entity.Factor;
import com.example.myapplication.Entity.LowerFactor;
import com.example.myapplication.Entity.NEO_NUMS;
import com.example.myapplication.Entity.QuestionItem;
import com.example.myapplication.Entity.SurveyData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class QuestionReaderForNEOSystem extends ReaderForNEOSystem{

    private int questionSerialNumber = 0; // 通し質問数カウンタ NEOPIなら240 NEOFFIなら60になるはず
    private String surveyMode;

    // contextはgetApplicationContext()で取得
    public QuestionReaderForNEOSystem(Context context, String filename, String surveyMode) {
        super(context, filename);
        this.surveyMode = surveyMode;
    }

    public SurveyData reader() throws Exception {
        SurveyData surveyData = null;
        // いったん全行読み取る
        try {
            // データファイルフォーマットはassets/NEOPI_Question.csv参照。
            ArrayList<Factor> factorList = new ArrayList<>();
            // 次元
            for (int factorCount = 0; factorCount < NEO_NUMS.FACTOR_SIZE.getNum(); factorCount++) {
                String factorTitle = bufferedReader.readLine();
                ArrayList<LowerFactor> lowerFactorList = new ArrayList<>();
                // 下位次元
                for (int lowerFactorCount = 0; lowerFactorCount < NEO_NUMS.LOWER_FACTOR_SIZE.getNum(); lowerFactorCount++) {
                    String lowerFactorTitle = bufferedReader.readLine();
                    // 質問
                    ArrayList<QuestionItem> questionItemList = makeQuestionItemList();
                    // 下位次元タイトルの設定とともにQuestionItemのエンティティリスト追加
                    lowerFactorList.add(new LowerFactor(lowerFactorTitle, questionItemList));
                    // 空の改行
                    bufferedReader.readLine();
                }
                factorList.add(new Factor(factorTitle,lowerFactorList));
            }
            surveyData = new SurveyData(factorList, surveyMode);
        //
        } catch (NullPointerException e) {
            e.printStackTrace();
            // TODO : nullで本当に停止するべきか要チェック
            throw new Exception("途中でファイルのnull領域を読んでしまったとか？");
        }
        // うまく読み込みができれば正しいインスタンスが無事戻る
        // それ以外は初期値のnullが戻る
        return surveyData;
    }

    private ArrayList<QuestionItem> makeQuestionItemList() {
        ArrayList<String> questionStringList = new ArrayList<>();
        // シャッフルするためのリスト作成にあたって、フルサイズでいったんすべて質問を抽出する
        for(int questionItemCount = 0; questionItemCount < NEO_NUMS.NEOPI_ITEM_SIZE.getNum(); questionItemCount++) {
            String questionString = null;
            try {
                questionString = this.bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            questionStringList.add(questionString);
            Log.v("questionString", questionString);
        }

        Collections.shuffle(questionStringList);

        // 抽出するアイテム数設定
        int questionItemSize = NEO_NUMS.NEOPI_ITEM_SIZE.getNum();
        if(surveyMode.equals("NEOFFI")) {
            questionItemSize = NEO_NUMS.NEOFFI_ITEM_SIZE.getNum();
        }

        // 生データからエンティティリストを作成して戻す
        // NEOFFIの場合8つの中から二つ抽出する
        // 質問LawDataの取得(フォーマット +,あいうえお)
        ArrayList<QuestionItem> questionItemList = new ArrayList<>();
        for (int index = 0; index < questionItemSize; index++, questionSerialNumber++) {
            String parts[] = questionStringList.get(index).split(SEPARATOR);
            String reverseSign = parts[NEO_NUMS.POSITION_REVERSE_SIGN.getNum()];
            String questionSentence = parts[NEO_NUMS.POSITION_QUESTION_SENTENCE.getNum()];
            questionItemList.add(new QuestionItem(questionSerialNumber,reverseSign,questionSentence));
        }
        return questionItemList;
    }
}