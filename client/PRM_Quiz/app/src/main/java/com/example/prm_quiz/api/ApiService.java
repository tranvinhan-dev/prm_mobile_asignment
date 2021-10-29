package com.example.prm_quiz.api;


import com.example.prm_quiz.model.LoginRequest;
import com.example.prm_quiz.model.LoginResponse;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.model.RegisterRequest;
import com.example.prm_quiz.model.RegisterResponse;
import com.example.prm_quiz.model.Subject;
import com.example.prm_quiz.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("authenticate")
    Call<LoginResponse> checkLogin(@Body LoginRequest loginRequest);
    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest rr);
    @GET("hello")
    Call<String> getSecretThings(@Header("Authorization") String token);
    @GET("api/v1/subjects")
    Call<List<Subject>> getALlSubject(@Header("Authorization") String token);
    @GET("api/v1/quizs")
    Call<List<Quiz>> getQuizs(@Header("Authorization") String token);
}
