package com.example.prm_quiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prm_quiz.R;
import com.example.prm_quiz.model.Score;

import java.util.List;

public class CustomListScoreForStudentAdapter extends BaseAdapter {

    private List<Score> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListScoreForStudentAdapter(Context aContext, List<Score> listData) {
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
            convertView = layoutInflater.inflate(R.layout.item_score_layout, null);
            holder = new ViewHolder();
            holder.tvQuizName = (TextView) convertView.findViewById(R.id.tvScoreQuizName);
            holder.tvScore = (TextView) convertView.findViewById(R.id.tvScore);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Score  score= (Score) this.listData.get(position);
        holder.tvQuizName.setText(score.getQuizName()+"");
        holder.tvScore.setText(score.getScore()+"");
        return convertView;
    }


    static class ViewHolder {
        TextView tvQuizName;
        TextView tvScore;
    }

}
