package com.example.prm_quiz.api;


import com.example.prm_quiz.model.DeleteResponse;
import com.example.prm_quiz.model.LoginRequest;
import com.example.prm_quiz.model.LoginResponse;
import com.example.prm_quiz.model.Question;
import com.example.prm_quiz.model.Quiz;
import com.example.prm_quiz.model.RegisterRequest;
import com.example.prm_quiz.model.RegisterResponse;
import com.example.prm_quiz.model.Score;
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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @GET("api/v1/users/username/{username}")
    Call<User> getUser(@Header("Authorization") String token, @Path("username") String username);
    @POST("api/v1/scores")
    Call<Score> putScore(@Header("Authorization") String token, @Body Score score);
    @GET("api/v1/scores")
    Call<List<Score>> getScore(@Header("Authorization") String token);
    @DELETE("api/v1/questions/{id}")
    Call<DeleteResponse> deleteQuestion(@Header("Authorization") String token, @Path("id") Long questionId);
    @DELETE("api/v1/quizs/{id}")
    Call<DeleteResponse> deleteQuiz(@Header("Authorization") String token, @Path("id") Long quizId);
    @PUT("api/v1/questions/{id}")
    Call<Question> updateQuestion(@Header("Authorization") String token, @Path("id") Long questionId, @Body Question question);
    @PUT("api/v1/quizs/{id}")
    Call<Quiz> updateQuiz(@Header("Authorization") String token, @Path("id") Long quizId, @Body Quiz quiz);
    @POST("api/v1/questions")
    Call<Question> updateQuestion(@Header("Authorization") String token, @Body Question question);
    @POST("api/v1/quizs")
    Call<Quiz> updateQuiz(@Header("Authorization") String token, @Body Quiz quiz);
}
