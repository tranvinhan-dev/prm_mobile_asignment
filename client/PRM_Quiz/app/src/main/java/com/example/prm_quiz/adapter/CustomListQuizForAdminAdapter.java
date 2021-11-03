package com.example.prm_quiz.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm_quiz.R;
import com.example.prm_quiz.TeacherHomeActivity;
import com.example.prm_quiz.UpdateQuizActivity;
import com.example.prm_quiz.api.ApiClient;
import com.example.prm_quiz.model.DeleteResponse;
import com.example.prm_quiz.model.Quiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomListQuizForAdminAdapter extends BaseAdapter {

    private List<Quiz> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private SharedPreferences prefs;
    public CustomListQuizForAdminAdapter(Context aContext, List<Quiz> listData, SharedPreferences prefs) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        this.prefs=prefs;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_quiz_admin_layout, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvScoreQuizName);
            holder.tvTeacherName = (TextView) convertView.findViewById(R.id.tvUpdateTeacherName);
            holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject2);
            holder.btnDelete= convertView.findViewById(R.id.btnDelete);
            holder.btnUpdate=convertView.findViewById(R.id.btnEdit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Quiz  quiz= (Quiz) this.listData.get(position);
        holder.tvName.setText(quiz.getName()+"");
        holder.tvTeacherName.setText(quiz.getTeacherName()+"");
        holder.tvSubject.setText(quiz.getSubject()+"");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to delete this quiz ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DeleteQuiz(quiz);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

            private void DeleteQuiz(Quiz quiz) {
                Call<DeleteResponse> userCall = ApiClient.getUserService().deleteQuiz("Bearer " + getToken(), quiz.getId());
                userCall.enqueue(new Callback<DeleteResponse>() {
                    @Override
                    public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                        try {
                            DeleteResponse isDeleted = response.body();
                            Toast.makeText(context, "call sucess quiz ", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(context, TeacherHomeActivity.class);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteResponse> call, Throwable t) {
                        Toast.makeText(context, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private String getToken() {
                String token = prefs.getString("token", "");
                return token;
            }


        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, UpdateQuizActivity.class);
                in.putExtra("quiz",quiz);
                context.startActivity(in);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        TextView tvName;
        TextView tvSubject;
        TextView tvTeacherName;
        Button btnUpdate;
        Button btnDelete;
    }

}
