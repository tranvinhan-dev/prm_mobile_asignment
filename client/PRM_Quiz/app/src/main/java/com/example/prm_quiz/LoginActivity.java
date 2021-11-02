package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.LoginRequest;
import com.example.prm_quiz.model.LoginResponse;
import com.example.prm_quiz.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    Button btnRegister;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapping();
        // them su kien cho nut dang nhap, goi api luu cookie va chuyen sang trang home
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);

            }
        });
        // chuyen trang sang trang sign up
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(_intent);
            }
        });
    }

    private void mapping() {
        //khoi tao prefs de luu token
        prefs = LoginActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit = prefs.edit();
        //anh xa cais components
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private void login(View view) {
        try {
            //lay du lieu username va password
            String username = edtUsername.getText().toString();

            String pass = edtPassword.getText().toString();
            //khoi tao ham login
//            if (username.length() != 0 && pass.length() != 0) {
                LoginRequest loginRequest = new LoginRequest(username, pass);
                //goi api
                Call<LoginResponse> userCall = ApiClient.getUserService().checkLogin(loginRequest);
                userCall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        try {
                            if (response.code() != 401) {
                                LoginResponse user = response.body();
                                String saveToken = user.getToken();
                                // them token vao sharedpreference
                                edit.putString("token", saveToken);
                                //ghi log
                                Log.i("Login", saveToken);
                                edit.commit();
                                //chuyen trang
                                getUser(view,saveToken,username);
                            } else {
                                Toast.makeText(LoginActivity.this, "Token Wrong user or password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }



                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                );
//            }
        } catch (Exception e) {
//            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    private void getUser(View view,String token,String username) {
        try {
            //lay du lieu username va password
            String tmp_user = username;

            String tmp_token = token;

            //khoi tao ham login
//            if (user.length() != 0 && tmp_token.length() != 0) {
                //goi api
                Call<User> userCall = ApiClient.getUserService().getUser("Bearer "+tmp_token,tmp_user);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        try {
                            if (response.code() != 401) {
                                User user = (User) response.body();
                                Toast.makeText(LoginActivity.this, response.body()+" response", Toast.LENGTH_SHORT).show();
//                                if(user !=null){
//                                    Toast.makeText(LoginActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
                                    //chuyen trang
                                    if(user!=null){
                                        if(user.getRole().equals("student")){
                                            Intent _intent = new Intent(LoginActivity.this, StudentHomeActivity.class);
                                            _intent.putExtra("user", user);
                                            startActivity(_intent);
                                        }else{
                                            Intent _intent = new Intent(LoginActivity.this, TeacherHomeActivity.class);
                                            _intent.putExtra("user", user);
                                            startActivity(_intent);
                                        }
                                    }


                            } else {
                                Toast.makeText(LoginActivity.this, "get User Wrong user or password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }



                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//            }
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}