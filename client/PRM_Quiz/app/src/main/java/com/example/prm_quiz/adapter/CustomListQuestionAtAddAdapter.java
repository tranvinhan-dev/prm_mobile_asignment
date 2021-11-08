package com.example.prm_quiz.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.prm_quiz.AddQuizActivity;
import com.example.prm_quiz.R;
import com.example.prm_quiz.UpdateQuizActivity;
import com.example.prm_quiz.model.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomListQuestionAtAddAdapter extends BaseAdapter {

    private List<Question> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListQuestionAtAddAdapter(Context aContext, List<Question> listData) {
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
            convertView = layoutInflater.inflate(R.layout.item_question_create_layout, null);
            holder = new ViewHolder();
            holder.edtQuestion = (EditText) convertView.findViewById(R.id.edtQuestion);
            holder.edtA = (EditText) convertView.findViewById(R.id.edtA);
            holder.edtB = (EditText) convertView.findViewById(R.id.edtB);
            holder.edtC = (EditText) convertView.findViewById(R.id.edtC);
            holder.edtD = (EditText) convertView.findViewById(R.id.edtD);
            holder.btnRemove=(Button) convertView.findViewById(R.id.btnRemove);
            holder.rbCorrectA = (RadioButton) convertView.findViewById(R.id.rbCorrectAnswerA);
            holder.rbCorrectB = (RadioButton) convertView.findViewById(R.id.rbCorrectAnswerB);
            holder.rbCorrectC = (RadioButton) convertView.findViewById(R.id.rbCorrectAnswerC);
            holder.rbCorrectD = (RadioButton) convertView.findViewById(R.id.rbCorrectAnswerD);
            holder.tv7 = convertView.findViewById(R.id.textView7);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question  question= (Question) this.listData.get(position);
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Remove", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder adb=new AlertDialog.Builder(context);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listData.remove(position);
                        CustomListQuestionAtAddAdapter.this.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });
        if(question.getId() != 0L){
            holder.edtQuestion.setText(question.getQuestion());
            holder.edtA.setText(question.getAnswerA());
            holder.edtB.setText(question.getAnswerB());
            holder.edtC.setText(question.getAnswerC());
            holder.edtD.setText(question.getAnswerD());
            String correctQuestion =question.getCorrectAnswer();
            if(correctQuestion.equals(question.getAnswerA())) holder.rbCorrectA.setChecked(true);
            if(correctQuestion.equals(question.getAnswerB())) holder.rbCorrectB.setChecked(true);
            if(correctQuestion.equals(question.getAnswerC())) holder.rbCorrectC.setChecked(true);
            if(correctQuestion.equals(question.getAnswerD())) holder.rbCorrectD.setChecked(true);

        }

        int pos = position +1;
        holder.tv7.setText("Question "+pos);

        holder.rbCorrectA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(question,position,holder);
                String answer = holder.edtA.getText()+"";
                question.setCorrectAnswer(answer);
                listData.set(position,question);
            }
        });
        holder.rbCorrectB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(question,position,holder);
                String answer = holder.edtB.getText()+"";
                question.setCorrectAnswer(answer);
                listData.set(position,question);
            }
        });
        holder.rbCorrectC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(question,position,holder);
                String answer = holder.edtC.getText()+"";
                question.setCorrectAnswer(answer);
                listData.set(position,question);
            }
        });
        holder.rbCorrectD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(question,position,holder);
                String answer = holder.edtD.getText()+"";
                question.setCorrectAnswer(answer);
                listData.set(position,question);
            }
        });
        holder.edtQuestion.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update(question,position,holder);
            }
        });
        holder.edtA.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update(question,position,holder);
            }
        });
        holder.edtB.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update(question,position,holder);
            }
        });
        holder.edtC.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update(question,position,holder);
            }
        });
        holder.edtD.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update(question,position,holder);
            }
        });
        return convertView;
    }

    private void update(Question question, int position, ViewHolder holder) {
        question.setAnswerA(holder.edtA.getText()+"");
        question.setAnswerB(holder.edtB.getText()+"");
        question.setAnswerC(holder.edtC.getText()+"");
        question.setAnswerD(holder.edtD.getText()+"");
        question.setQuestion(holder.edtQuestion.getText()+"");
        listData.set(position,question);
    }

    public List<Question> getListData() {
        return listData;
    }

    public void setListData(List<Question> listData) {
        this.listData = listData;
    }

    public void addMoreQuestion() {
        listData.add(new Question(0L,"","","","","",""));
    }

    public void remove(int i) {
        listData.remove(i);
    }


    static class ViewHolder {
        EditText edtQuestion;
        EditText edtA;
        EditText edtB;
        EditText edtC;
        EditText edtD;
        RadioButton rbCorrectA;
        RadioButton rbCorrectB;
        RadioButton rbCorrectC;
        RadioButton rbCorrectD;
        RadioGroup rgGroup;
        TextView tv7;
        Button btnRemove;
    }

}
