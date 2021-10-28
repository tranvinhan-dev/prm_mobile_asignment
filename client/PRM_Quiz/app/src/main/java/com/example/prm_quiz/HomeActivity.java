package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.LoginRequest;
import com.example.prm_quiz.model.LoginResponse;
import com.example.prm_quiz.model.Question;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.model.Subject;
import com.example.prm_quiz.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    TextView tvUser;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        clickCallApi(getToken());
        //get list of quiz
        List<Quiz> image_details = getListData();
        final ListView listView = (ListView) findViewById(R.id.lvQuiz);
        listView.setAdapter(new CustomListAdapter(HomeActivity.this, image_details));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Quiz quiz = (Quiz) o;
                Intent _intent = new Intent(HomeActivity.this, QuizActivity.class);
//                Bundle bundle =new Bundle();
//                bundle.putSerializable("quiz", quiz);
//                _intent.putExtras(bundle);
                _intent.putExtra("quiz",quiz);
                startActivity(_intent);
            }
        });
        Button btnScore = findViewById(R.id.btnScore);
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ScoreActivity.class);
                startActivity(intent);
            }
        });
        Button btnAdd = findViewById(R.id.btnAddQuiz);
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AddQuizActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getToken() {
        prefs = HomeActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        return token;
    }

    private void clickCallApi(String token) {
        Call<List<Subject>> userCall = ApiClient.getUserService().getALlSubject("Bearer " + token);
        userCall.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                try {
                    //get list of subject
                    List<Subject> lt = response.body();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < lt.size(); i++) {
                        list.add(lt.get(i).getName());
                    }
//                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(HomeActivity.this,
//                            android.R.layout.simple_spinner_dropdown_item, list);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    Spinner spinner1 = findViewById(R.id.snSubject);
//                    spinner1.setAdapter(dataAdapter);

                } catch (Exception e) {
                    Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Quiz> getListData() {
        List<Question> listQuestion = new ArrayList<>();
        listQuestion.add(new Question("1","What is pig mean ?","con cho","con heo","con ga","con bo","con heo","1"));
        listQuestion.add(new Question("2","What is dog mean ?","con cho","con heo","con ga","con bo","con cho","1"));
        listQuestion.add(new Question("3","What is chicken mean ?","con cho","con heo","con ga","con bo","con ga","1"));

        List<Quiz> list = new ArrayList<>();
        Quiz vietnam = new Quiz("1", "Quiz-1", "Nam", "Math", "123456", 60000, listQuestion);
        list.add(vietnam);
//        list.add(usa);
//        list.add(russia);
//        list.add(australia);
//        list.add(japan);

        return list;
    }
}