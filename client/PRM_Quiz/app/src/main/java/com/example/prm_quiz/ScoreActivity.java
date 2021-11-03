package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prm_quiz.adapter.CustomListQuizForStudentAdapter;
import com.example.prm_quiz.adapter.CustomListScoreForStudentAdapter;
import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.model.Score;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoreActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    List<Score> scoreList  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getListData(getToken());
        Button btnBack = findViewById(R.id.btnBack2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private String getToken() {
        prefs = ScoreActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        return token;
    }
    private void getListData(String token) {
        Call<List<Score>> userCall = ApiClient.getUserService().getScore("Bearer " + token);
        userCall.enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                try {

                    //get list of subject
                    scoreList = response.body();
                    if (!scoreList.isEmpty()) {
                        List<Score> image_details = scoreList;
                        final ListView listView = (ListView) findViewById(R.id.lvScore);
                        listView.setAdapter(new CustomListScoreForStudentAdapter(ScoreActivity.this, image_details));
                    }
                    Toast.makeText(ScoreActivity.this, "call sucess", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ScoreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                Toast.makeText(ScoreActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}