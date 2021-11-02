package com.example.prm_quiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.prm_quiz.R;
import com.example.prm_quiz.model.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomListQuestionAdapter extends BaseAdapter {

    private List<Question> listData;
    private HashMap<Integer,Boolean> listResult;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListQuestionAdapter(Context aContext, List<Question> listData) {
        this.context = aContext;
        this.listData = listData;
        listResult= new HashMap<>();
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
            convertView = layoutInflater.inflate(R.layout.item_question_layout, null);
            holder = new ViewHolder();
            holder.tvQuestion = (TextView) convertView.findViewById(R.id.tvQuestion);
            holder.rbA = (RadioButton) convertView.findViewById(R.id.rbA);
            holder.rbB = (RadioButton) convertView.findViewById(R.id.rbB);
            holder.rbC = (RadioButton) convertView.findViewById(R.id.rbC);
            holder.rbD = (RadioButton) convertView.findViewById(R.id.rbD);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question  question= (Question) this.listData.get(position);
        holder.tvQuestion.setText(question.getQuestion()+"");
        holder.rbA.setText(question.getAnswerA()+"");
        holder.rbB.setText(question.getAnswerB()+"");
        holder.rbC.setText(question.getAnswerC()+"");
        holder.rbD.setText(question.getAnswerD()+"");
        holder.rbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = holder.rbA.getText()+"";
                listResult.put(position,answer.trim().equals(question.getCorrectAnswer()));
            }
        });
        holder.rbB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = holder.rbB.getText()+"";
                listResult.put(position,answer.trim().equals(question.getCorrectAnswer()));
            }
        });
        holder.rbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = holder.rbC.getText()+"";
                listResult.put(position,answer.trim().equals(question.getCorrectAnswer()));
            }
        });
        holder.rbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = holder.rbD.getText()+"";
                listResult.put(position,answer.trim().equals(question.getCorrectAnswer()));
            }
        });
        return convertView;
    }
    public int getResult(){
        int total=0;
        for(Map.Entry<Integer, Boolean> entry : listResult.entrySet()) {
            Integer key = entry.getKey();
            Boolean value = entry.getValue();
            if(value){
                total ++;
            }
            // do what you have to do here
            // In your case, another loop.
        }
        return total;
    }

    static class ViewHolder {
        TextView tvQuestion;
        RadioButton rbA;
        RadioButton rbB;
        RadioButton rbC;
        RadioButton rbD;
    }

}
