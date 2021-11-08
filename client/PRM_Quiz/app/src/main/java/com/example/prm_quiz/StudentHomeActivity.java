package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prm_quiz.adapter.CustomListQuizForStudentAdapter;
import com.example.prm_quiz.adapter.PhotoViewPage2Adapter;
import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.Photo;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.model.Subject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentHomeActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    List<Quiz> quizList;
    //banner
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private List<Photo> mListPhoto;
    //auto-run banner
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == mListPhoto.size() - 1){
                mViewPager2.setCurrentItem(0);
            } else {
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() + 1);
            }
        }
    };
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
        //banner
        mViewPager2 = findViewById(R.id.view_pager_2);
        mCircleIndicator3 = findViewById(R.id.circle_indicator_3);
        mListPhoto = getListPhoto();
        PhotoViewPage2Adapter adapter = new PhotoViewPage2Adapter(mListPhoto);
        mViewPager2.setAdapter(adapter);
        //connect mCircleIndicator3 with mViewPager2
        mCircleIndicator3.setViewPager(mViewPager2);
        //auto-run banner
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 3000);
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

    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.p1));
        list.add(new Photo(R.drawable.p2));
        list.add(new Photo(R.drawable.p3));
        return list;
    }

    @Override
    protected void onPause() {//banner - When out the app (ex: Press 'home')
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 3000);
    }
}