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
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.api.ApiService;
import com.example.prm_quiz.model.LoginRequest;
import com.example.prm_quiz.model.LoginResponse;
import com.example.prm_quiz.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText tv;
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs=MainActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit=prefs.edit();
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallApi(view);
            }
        });
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(_intent);
            }
        });
    }

    private void clickCallApi(View view) {
        try{
            String user = edtUsername.getText().toString();
            String pass = edtPassword.getText().toString();
            LoginRequest loginRequest = new LoginRequest(user, pass);
            Call<LoginResponse> userCall = ApiClient.getUserService().checkLogin(loginRequest);
            userCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    try {
                        if(response.code() != 401){
                            Intent _intent = new Intent(MainActivity.this, HomeActivity.class);
                            LoginResponse user = response.body();
                            String saveToken=user.getToken();
                            edit.putString("token",saveToken);
                            Log.i("Login",saveToken);
                            edit.commit();
                            _intent.putExtra("user", user);
                            startActivity(_intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Wrong user or password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Call Api Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch(Exception e){
//            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}