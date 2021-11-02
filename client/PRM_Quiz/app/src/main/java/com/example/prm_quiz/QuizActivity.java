package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.prm_quiz.adapter.CustomListQuestionAdapter;
import com.example.prm_quiz.model.Question;
import com.example.prm_quiz.model.Quiz;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        Quiz q =(Quiz) b.getSerializable("quiz");
        List<Question> n = q.getListQuestion();
        TextView tvCount = findViewById(R.id.tvCountTime2);
        final ListView listView = (ListView) findViewById(R.id.lvQuestion);
        CustomListQuestionAdapter adapter = new CustomListQuestionAdapter(QuizActivity.this, q.getListQuestion());
        listView.setAdapter(adapter);
        CountDownTimer cdTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCount.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
            Intent _intent = new Intent(QuizActivity.this, ResultActivity.class);
           Bundle bundle =new Bundle();
           bundle.putInt("size",q.getListQuestion().size());
           bundle.putInt("result",adapter.getResult());
           _intent.putExtras(bundle);
            startActivity(_intent);
            }

        }.start();
        Button btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdTimer.onFinish(); cdTimer.cancel();
            }
        });
    }
}