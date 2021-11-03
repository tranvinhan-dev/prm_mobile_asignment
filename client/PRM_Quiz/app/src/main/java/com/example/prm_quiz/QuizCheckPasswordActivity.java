package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.util.MyEncryption;

public class QuizCheckPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_check_password);
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        Quiz q = (Quiz) b.getSerializable("quiz");

        Button btnDoQuiz = findViewById(R.id.btnCheckQuizPassword);
        btnDoQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView error = findViewById(R.id.tvError);

                //checkPassword
                String password= q.getPassword();
                String name = q.getName();
                EditText edtPassword = findViewById(R.id.edtConfirmPassword);
                String userInputPassword = edtPassword.getText()+"";
                MyEncryption me = new MyEncryption();
                if(userInputPassword.length()!=0){
                    String encryptPass = null;
                    try {
                        encryptPass = me.getMyEncryption(name, userInputPassword);
                    } catch (Exception e){}
                    if(password.equals(encryptPass)){
                        Intent _intent = new Intent(QuizCheckPasswordActivity.this, QuizActivity.class);
                        _intent.putExtra("quiz", q);
                        startActivity(_intent);
                    }
                    else{
                        error.setText("Password does not match your input password !");
                    }
                }else{
                    error.setText("Please input password !");
                }

            }
        });
    }
}