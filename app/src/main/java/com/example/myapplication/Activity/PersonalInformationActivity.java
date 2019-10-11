package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

// TODO: 一画面ずつ遷移していくスタイル
public class PersonalInformationActivity extends AppCompatActivity {

    private Button startButton;

    private void transitionSurveyScreen(int age, String gender, String mode, String debug){
        // 画面遷移
        //startButton.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
        intent.putExtra("AGE",age);
        intent.putExtra("GENDER",gender);
        intent.putExtra("MODE",mode);
        intent.putExtra("DEBUG",debug);
        startActivity(intent);
    }

    private void showAttentionDialog(){
        AlertDialog attention = new AlertDialog.Builder(this)
                .setTitle("入力エラー")
                .setMessage("性別か年齢の入力が不正です")
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int age = getEnteredAge();
                String gender=getSelectedLabelGender();
                String mode=getCheckedLabelMode();
                String debug = getCheckedLabelDebug();

                if(checkInRange(age) && !gender.equals("未選択") && !mode.equals("未選択") &&!debug.equals("未選択")) {
                    transitionSurveyScreen(age,gender,mode,debug);
                }else{
                    showAttentionDialog();
                }
            }

            private boolean checkInRange(int age){
                return (0 <= age) && (age <= 120);
            }

            private int getEnteredAge(){
                TextView numberAge = findViewById(R.id.numberAge);
                try{
                    return Integer.parseInt(numberAge.getText().toString());
                }catch(NumberFormatException e){
                    return -1;
                }
            }

            private String getSelectedLabelGender(){
                RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
                int checkedGender = radioGroupGender.getCheckedRadioButtonId();
                if(checkedGender == -1) {
                    return "未選択";
                }else{
                    RadioButton selectedGender = findViewById(checkedGender);
                    return selectedGender.getText().toString(); // 選択された性別のラベル文字列を返す
                }
            }

            private String getCheckedLabelMode(){
                RadioGroup radioGroupMode = findViewById(R.id.radioGroupMode);
                int checkedMode = radioGroupMode.getCheckedRadioButtonId();
                if(checkedMode == -1) {
                    return "未選択";
                }else{
                    RadioButton selectedMode = findViewById(checkedMode);
                    return selectedMode.getText().toString();
                }
            }

            private String getCheckedLabelDebug(){
                RadioGroup radioGroupDebug = findViewById(R.id.radioGroupDebug);
                int checkedDebug = radioGroupDebug.getCheckedRadioButtonId();
                if(checkedDebug == -1) {
                    return "未選択";
                }else{
                    RadioButton selectedMode = findViewById(checkedDebug);
                    return selectedMode.getText().toString();
                }
            }
        });
    }
}
