package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddQuizActivity extends AppCompatActivity {

    private Button btnAddQuiz;
    private Button btnCancelAddQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        btnAddQuiz = (Button) findViewById(R.id.btnAddQuiz);
        btnCancelAddQuiz = (Button) findViewById(R.id.btnCancelAddQuiz);

        btnAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCancelAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}