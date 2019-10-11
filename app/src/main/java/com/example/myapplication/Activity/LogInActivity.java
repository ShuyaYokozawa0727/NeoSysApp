package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class LogInActivity extends AppCompatActivity {
    private Button sendButton;
    private EditText inputPassword;
    private EditText inputID;
    private TextView outputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sendButton = findViewById(R.id.sendButton);
        inputPassword = findViewById(R.id.inputPassword);
        outputPassword = findViewById(R.id.outputPassword);
        inputID = findViewById(R.id.inputID);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendButton.setVisibility(View.INVISIBLE);
                String id = inputID.getText().toString();
                String password = inputPassword.getText().toString();
                Intent intent = new Intent(getApplicationContext(), PersonalInformationActivity.class);
                intent.putExtra("ID",id);
                intent.putExtra("PASSWORD",password);
                startActivity(intent);
                finish();
            }
        });
    }
}
