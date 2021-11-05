package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        int result = bundle.getInt("result");
        int size = bundle.getInt("size");
        TextView tvNoCorrect = findViewById(R.id.tvNoCorrect);
        TextView tvResult = findViewById(R.id.tvQuizScore);
        tvNoCorrect.setText("Total Correct: " + result + " /" + size);
        tvResult.setText("Your Score: " + (result * 100) + " /" + (size * 100) );
        Button btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(ResultActivity.this, StudentHomeActivity.class);
                startActivity(t);
            }
        });
    }
}