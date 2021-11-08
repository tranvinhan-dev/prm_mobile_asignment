package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_quiz.adapter.CustomListQuestionAtAddAdapter;
import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.Question;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.util.MyEncryption;
import com.example.prm_quiz.util.MyVar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddQuizActivity extends AppCompatActivity {

    private Button btnAddQuiz;
    private Button btnCancelAddQuiz;
    SharedPreferences prefs;
    private EditText qName;
    private TextView qTeacherName;
    private EditText qSubject;
    private EditText qPassword;
    private EditText qConfirmPassword;
    private EditText qTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        btnAddQuiz = (Button) findViewById(R.id.btnAddQuiz);
        btnCancelAddQuiz = (Button) findViewById(R.id.btnCancelAddQuiz);
        qName = (EditText) findViewById(R.id.edtQuizName);
        qTeacherName = (TextView) findViewById(R.id.tvTeacherName);
        qTeacherName.setText(getName());
        qSubject = (EditText) findViewById(R.id.edtSubjectName);
        qPassword = (EditText) findViewById(R.id.edtQuizPassword);
        qConfirmPassword = (EditText) findViewById(R.id.edtQuizConfirmPassword);
        qTime = (EditText) findViewById(R.id.edtQuizTime);
        List<Question> listQuestion = getListData();
        final ListView listView = (ListView) findViewById(R.id.lvQuestion);
        CustomListQuestionAtAddAdapter adapter = new CustomListQuestionAtAddAdapter(AddQuizActivity.this,listQuestion);
        listView.setAdapter(adapter);
        Button btnAddmore = findViewById(R.id.btnAddMoreQuestion);
        btnAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addMoreQuestion();
                adapter.notifyDataSetChanged();

            }
        });
        btnAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = "";
                if(TextUtils.isEmpty(qName.getText())
                    || TextUtils.isEmpty(qTeacherName.getText())
                    || TextUtils.isEmpty(qSubject.getText())
                    || TextUtils.isEmpty(qPassword.getText())
                    || TextUtils.isEmpty(qTime.getText())
                ) {
                    msg = "All information must be filled";
                } else {
                    if(! qPassword.getText().toString()
                            .equals(qConfirmPassword.getText().toString())) {
                        msg = "Password & ConfirmPassword must match";
                    } else {
                        MyEncryption me = new MyEncryption();

                        Long id = (long) 1;
                        String name = qName.getText().toString();
                        String teacherName = qTeacherName.getText().toString();
                        String subject = qSubject.getText().toString();
                        String password = qPassword.getText().toString();
                        String encryptPass = "";
                        try {
                            encryptPass = me.getMyEncryption(name, password);
                        } catch (Exception e) {}
                        int time = Integer.parseInt(qTime.getText().toString());
                        if (time >= MyVar.MIN_QUIZ_TIME) {
                            List<Question> listQuestion = adapter.getListData();
                            //return quiz
                            Quiz quiz = new Quiz(id, name, teacherName, subject, encryptPass, time, listQuestion);
                            //add quiz
                            addQuiz(getToken(), quiz);
                            msg = "Add quiz successful";
                        } else {
                            msg = "Quiz Time must at least more than 5 seconds";
                        }
                    }
                }
                Toast.makeText(AddQuizActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private String getToken() {
        prefs = AddQuizActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        return token;
    }
    private String getName() {
        prefs = AddQuizActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("name", "null");
        return token;
    }
    private List<Question> getListData() {
       List<Question> listQuestion = new ArrayList<>();
       listQuestion.add(new Question(0L,"","","","","",""));
        return listQuestion;
    }
    private void addQuiz(String token,Quiz quiz) {
        Call<Quiz> userCall = ApiClient.getUserService().addQuiz("Bearer " + token,quiz);
        userCall.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                try {
                    Toast.makeText(AddQuizActivity.this, "call sucess", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(AddQuizActivity.this,TeacherHomeActivity.class);
                    startActivity(in);
                } catch (Exception e) {
                    Toast.makeText(AddQuizActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                Toast.makeText(AddQuizActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}