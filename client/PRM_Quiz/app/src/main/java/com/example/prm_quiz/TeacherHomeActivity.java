package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prm_quiz.adapter.CustomListQuizForAdminAdapter;
import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.Quiz;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherHomeActivity extends AppCompatActivity {
    SharedPreferences prefs;
    List<Quiz> quizList  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        String token = getToken();
        //get list of quiz
        quizList =new ArrayList<>();
        getListData(token);
        Button btnAdd = findViewById(R.id.btnAddQuiz);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherHomeActivity.this, AddQuizActivity.class);
                startActivity(intent);
            }
        });
        Button btnLogout = findViewById(R.id.btnLogout2);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeToken();
                Intent intent = new Intent(TeacherHomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void removeToken() {
        prefs = TeacherHomeActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        prefs.edit().remove("token").commit();

    }

    private String getToken() {
        prefs = TeacherHomeActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
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
                        listView.setAdapter(new CustomListQuizForAdminAdapter(TeacherHomeActivity.this, image_details,prefs));
                    }
                    Toast.makeText(TeacherHomeActivity.this, "call sucess "+quizList.get(0).getListQuestion().get(0).toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TeacherHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Quiz>> call, Throwable t) {
                Toast.makeText(TeacherHomeActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}