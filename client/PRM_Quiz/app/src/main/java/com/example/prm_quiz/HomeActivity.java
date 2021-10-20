package com.example.prm_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.LoginRequest;
import com.example.prm_quiz.model.LoginResponse;
import com.example.prm_quiz.model.User;

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
        tvUser = findViewById(R.id.tvUserInfo);
        clickCallApi(getToken());
    }
    private String getToken() {
        prefs=HomeActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return token;
    }
    private void clickCallApi(String token) {
        Call<String> userCall = ApiClient.getUserService().getSecretThings("Bearer "+token);
        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try{
                    tvUser.setText(response.body().toString());
                }
                catch (Exception e){
                    Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Call Api Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}