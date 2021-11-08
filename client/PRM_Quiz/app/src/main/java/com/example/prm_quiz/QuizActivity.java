package com.example.prm_quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_quiz.adapter.CustomListQuestionAdapter;
import com.example.prm_quiz.adapter.CustomListQuizForStudentAdapter;
import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.Question;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.model.Score;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    int result, size;
    Long quizId, userId;
    String quizName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        Quiz q = (Quiz) b.getSerializable("quiz");
        List<Question> n = q.getListQuestion();
        TextView tvCount = findViewById(R.id.tvCountTime2);
        final ListView listView = (ListView) findViewById(R.id.lvQuestion);
        CustomListQuestionAdapter adapter = new CustomListQuestionAdapter(QuizActivity.this, q.getListQuestion());
        listView.setAdapter(adapter);
        CountDownTimer cdTimer = new CountDownTimer(q.getTime(), 1000) {

            public void onTick(long millisUntilFinished) {
                tvCount.setText("Time remaining: " + millisUntilFinished / 1000 + " seconds.");
                //here you can have your logic to set text to edittext
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onFinish() {
                size = q.getListQuestion().size();
                result = adapter.getResult();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String time = dtf.format(now);
                Score score = new Score(0L, getUserid(), q.getId(), q.getName(), result, size, time);
                putScore(getToken(), score);
            }

        }.start();
        Button btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdTimer.onFinish();
                cdTimer.cancel();
            }
        });
    }

    private String getToken() {
        prefs = QuizActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        return token;
    }

    private Long getUserid() {
        prefs = QuizActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Long token = prefs.getLong("id", 0);
        return token;
    }

    private void putScore(String token, Score score) {
        Call<Score> userCall = ApiClient.getUserService().putScore("Bearer " + token, score);
        userCall.enqueue(new Callback<Score>() {
            @Override
            public void onResponse(Call<Score> call, Response<Score> response) {
                try {

                    //get list of subject
                    Score score = response.body();
                    if (score != null) {
                        Intent _intent = new Intent(QuizActivity.this, ResultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("size", size);
                        bundle.putInt("result", result);
                        _intent.putExtras(bundle);
                        startActivity(_intent);
                    }
                    Toast.makeText(QuizActivity.this, "call sucess", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(QuizActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Score> call, Throwable t) {
                Toast.makeText(QuizActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}