package com.example.prm_quiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prm_quiz.R;
import com.example.prm_quiz.model.Quiz;

import java.util.List;

public class CustomListQuizForStudentAdapter extends BaseAdapter {

    private List<Quiz> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListQuizForStudentAdapter(Context aContext, List<Quiz> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
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
            convertView = layoutInflater.inflate(R.layout.list_item_quiz_student_layout, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvScoreQuizName);
            holder.tvTeacherName = (TextView) convertView.findViewById(R.id.tvTeacherName);
            holder.tvSubject = (TextView) convertView.findViewById(R.id.tvScore);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Quiz  quiz= (Quiz) this.listData.get(position);
        holder.tvName.setText(quiz.getName()+"");
        holder.tvTeacherName.setText(quiz.getTeacherName()+"");
        holder.tvSubject.setText(quiz.getSubject()+"");
        return convertView;
    }


    static class ViewHolder {
        TextView tvName;
        TextView tvSubject;
        TextView tvTeacherName;
    }

}
