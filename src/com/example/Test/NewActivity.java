package com.example.Test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.*;


public class NewActivity extends Activity implements View.OnClickListener {
    TextView question;
    Button yesBut;
    ProgressBar progressBar;

    ArrayList<Questions> data = null;
    int correctAnswers = 0;
    int counter;
    int quesAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.new_layout);


        counter = 0;

        question = (TextView) findViewById(R.id.quesText);
        yesBut = (Button) findViewById(R.id.yes);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        yesBut.setOnClickListener(this);

        try {
            Questions q = new Questions();
            data = q.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        quesAmount = intent.getIntExtra("quesAmount", quesAmount);

        question.setText(data.get(counter).question);
        createRadioButton(data.get(counter).answers);
        progressBar.setMax(quesAmount);
        progressBar.setProgress(counter+1);
        counter++;
    }

    RelativeLayout ll;
    RadioButton[] rb;
    RadioGroup rg;

    void createRadioButton(String[] ans) {
        ll = (RelativeLayout) findViewById(R.id.ansLayout);
        rb = new RadioButton[ans.length];
        rg = new RadioGroup(this);
        rg.setOrientation(RadioGroup.VERTICAL);
        for(int i = 0; i < ans.length; i++){
            rb[i]  = new RadioButton(this);
            rb[i].setText(ans[i]);
            rg.addView(rb[i]);
        }
        ll.addView(rg);
    }

    int checkAnswer () {
        int a = 0;
        for (int i = 0; i < rb.length; i++) {
            if (rb[i].isChecked()) a = i+1;
        }
        return a;
    }

    @Override
    public void onClick(View view) {
        if (rg.getCheckedRadioButtonId() != -1) {
            if (checkAnswer() == data.get(counter-1).correct) correctAnswers++;
            if (counter < quesAmount) {
                switch (view.getId()) {
                    case R.id.yes:
                        rg.removeAllViews();
                        question.setText(data.get(counter).question);
                        createRadioButton(data.get(counter).answers);
                        progressBar.setProgress(counter+1);
                        counter++;
                        break;
                }
            } else {showAlertWithOneButton();}
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }

    public void showAlertWithOneButton (){
        AlertDialog alertDialog = new AlertDialog.Builder(NewActivity.this).create();
        alertDialog.setTitle("Твій результат");
        alertDialog.setMessage(correctAnswers + " з " + quesAmount + '\n' + "Зможеш краще?");
        alertDialog.setButton("Так!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onDestroy();
            }
        });
        alertDialog.setButton2("Ні, дякую...", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
