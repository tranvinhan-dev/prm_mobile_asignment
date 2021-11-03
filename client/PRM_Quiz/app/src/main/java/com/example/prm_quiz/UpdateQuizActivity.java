package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateQuizActivity extends AppCompatActivity {
    private Button btnUpdateQuiz;
    private Button btnCancelUpdateQuiz;
    SharedPreferences prefs;
    private EditText qName;
    private TextView qTeacherName;
    private EditText qSubject;
    private EditText qPassword;
    private EditText NewPassword;
    private EditText ConfirmPassword;
    private EditText qTime;
    private Long qid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        Quiz q = (Quiz) b.getSerializable("quiz");
        qid= q.getId();
        btnUpdateQuiz = (Button) findViewById(R.id.btnUpdateQuiz);
        btnCancelUpdateQuiz = (Button) findViewById(R.id.btnCancelUpdateQuiz);
        qName = (EditText) findViewById(R.id.edtUpdateQuizName);
        qTeacherName = (TextView) findViewById(R.id.tvUpdateTeacherName);
        qTeacherName.setText(getName());
        qSubject = (EditText) findViewById(R.id.edtUpdateSubjectName);
        qPassword = (EditText) findViewById(R.id.edtOldPassword);
        NewPassword = (EditText) findViewById(R.id.edtNewPassword);
        ConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        qTime = (EditText) findViewById(R.id.edtUpdateQuizTime);
        qName.setText(q.getName());
        qSubject.setText(q.getSubject());
        qPassword.setText(q.getPassword());
        qTime.setText(q.getTime()+"");
        List<Question> listQuestion = q.getListQuestion();
        final ListView listView = (ListView) findViewById(R.id.lvUpdateQuestion);
        CustomListQuestionAtAddAdapter adapter = new CustomListQuestionAtAddAdapter(UpdateQuizActivity.this,listQuestion);
        listView.setAdapter(adapter);
        Button btnAddmore = findViewById(R.id.btnAddMoreQuestion2);
        btnAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addMoreQuestion();
                adapter.notifyDataSetChanged();

            }
        });
        btnUpdateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEncryption me = new MyEncryption();

                Long id = qid;
                String name = qName.getText().toString();
                String teacherName = qTeacherName.getText().toString();
                String subject = qSubject.getText().toString();
                String password = NewPassword.getText().toString();
                String encryptPass = "";
                try {
                    encryptPass = me.getMyEncryption(name, qPassword.getText().toString());
                } catch (Exception e){}
                int time = Integer.parseInt(qTime.getText().toString());
                List<Question> listQuestion = adapter.getListData();
                //return quiz
                Quiz quiz = new Quiz(id, name, teacherName, subject, encryptPass, time, listQuestion);
                //add quiz
                updateQuiz(getToken(),quiz);
            }
        });

        btnCancelUpdateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private String getToken() {
        prefs = UpdateQuizActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", "");
        return token;
    }
    private String getName() {
        prefs = UpdateQuizActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("name", "null");
        return token;
    }
    private List<Question> getListData() {
        List<Question> listQuestion = new ArrayList<>();
        listQuestion.add(new Question(0L,"","","","","",""));
        return listQuestion;
    }
    private void updateQuiz(String token,Quiz quiz) {
        Call<Quiz> userCall = ApiClient.getUserService().updateQuiz("Bearer " + token,quiz.getId(),quiz);
        userCall.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                try {
                    Toast.makeText(UpdateQuizActivity.this, "update call sucess", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(UpdateQuizActivity.this,TeacherHomeActivity.class);
                    startActivity(in);
                } catch (Exception e) {
                    Toast.makeText(UpdateQuizActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                Toast.makeText(UpdateQuizActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}