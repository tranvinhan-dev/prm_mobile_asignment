package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.RegisterRequest;
import com.example.prm_quiz.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUsername2;
    EditText edtPassword2;
    EditText edtName;
    EditText edtConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtUsername2=findViewById(R.id.edtUsername2);
        edtPassword2=findViewById(R.id.edtPassword2);
        edtName=findViewById(R.id.edtName);
        edtConfirm=findViewById(R.id.edtConfirm);
        Button btnRegister2 = findViewById(R.id.btnRegister2);
        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallApi(view);
            }
        });
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void clickCallApi(View view) {
        try{
            String user = edtUsername2.getText().toString();
            String pass = edtPassword2.getText().toString();
            String name = edtName.getText().toString();
            String confirm = edtConfirm.getText().toString();
            if(confirm.equals(pass)){
                RegisterRequest rr = new RegisterRequest(user,pass,name);
                Call<RegisterResponse> userCall = ApiClient.getUserService().register(rr);
                userCall.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        try {
                            if(response.code() != 401){
                                Intent _intent = new Intent(RegisterActivity.this, StudentHomeActivity.class);
                                RegisterResponse user = response.body();
                                startActivity(_intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch(Exception e){
//            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}