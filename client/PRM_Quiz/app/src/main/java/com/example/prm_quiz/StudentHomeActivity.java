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
import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.model.Subject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentHomeActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    List<Quiz> quizList  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        String token = getToken();
        //get list of quiz
        quizList =new ArrayList<>();
        getListData(token);
        Button btnScore = findViewById(R.id.btnScore);
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentHomeActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeToken();
                Intent intent = new Intent(StudentHomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void removeToken() {
        prefs = StudentHomeActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        prefs.edit().remove("token").commit();

    }
    private String getToken() {
        prefs = StudentHomeActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        return token;
    }

    private void getListData(String token) {
        Call<List<Quiz>> userCall = ApiClient.getUserService().getQuizs("Bearer " + token);
        userCall.enqueue(new Callback<List<Quiz>>() {
            @Override
            public void onResponse(Call<List<Quiz>> call, Response<List<Quiz>> response) {
                try {

                    //get list of subject
                    quizList = response.body();
                    if (!quizList.isEmpty()) {
                        List<Quiz> image_details = quizList;
                        final ListView listView = (ListView) findViewById(R.id.lvQuiz2);
                        listView.setAdapter(new CustomListQuizForStudentAdapter(StudentHomeActivity.this, image_details));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Object o = listView.getItemAtPosition(position);
                                Quiz quiz = (Quiz) o;
                                Intent _intent = new Intent(StudentHomeActivity.this, QuizCheckPasswordActivity.class);
                                _intent.putExtra("quiz", quiz);
                                startActivity(_intent);
                            }
                        });
                    }
                    Toast.makeText(StudentHomeActivity.this, "call sucess", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(StudentHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(StudentHomeActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}