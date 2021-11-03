package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prm_quiz.model.Question;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.model.User;
import com.example.prm_quiz.util.MyEncryption;

import java.util.List;

public class AddQuizActivity extends AppCompatActivity {

    private Button btnAddQuiz;
    private Button btnCancelAddQuiz;

    private EditText qName;
    private TextView qTeacherName;
    private EditText qSubject;
    private EditText qPassword;
    private EditText qTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        btnAddQuiz = (Button) findViewById(R.id.btnAddQuiz);
        btnCancelAddQuiz = (Button) findViewById(R.id.btnCancelAddQuiz);

        User user = (User) getIntent().getSerializableExtra("user");

        qName = (EditText) findViewById(R.id.edtQuizName);
        qTeacherName = (TextView) findViewById(R.id.tvTeacherName);
        qTeacherName.setText(user.getName());
        qSubject = (EditText) findViewById(R.id.edtSubjectName);
        qPassword = (EditText) findViewById(R.id.edtQuizPassword);
        qTime = (EditText) findViewById(R.id.edtQuizTime);

        btnAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyEncryption me = new MyEncryption();

                Long id = (long) 1;
                String name = qName.getText().toString();
                String teacherName = qTeacherName.getText().toString();
                String subject = qSubject.getText().toString();
                String password = qPassword.getText().toString();
                String encryptPass = "";
                try {
                    encryptPass = me.getMyEncryption(name, qPassword.getText().toString());
                } catch (Exception e){}
                int time = Integer.parseInt(qTime.getText().toString());
                List<Question> listQuestion = null;

                //return quiz
                Quiz quiz = new Quiz(id, name, teacherName, subject, encryptPass, time, listQuestion);
            }
        });

        btnCancelAddQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}