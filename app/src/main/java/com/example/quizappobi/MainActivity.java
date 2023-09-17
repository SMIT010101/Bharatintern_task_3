package com.example.quizappobi;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalque;
    TextView que;
    Button ansa, ansb, ansc;
    Button submit;
    int score = 0;
    int totalQuestion = Qns.que.length;
    int currentque = 0;
    String selectAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalque = findViewById(R.id.Totalque);
        que = findViewById(R.id.Que);
        ansa = findViewById(R.id.Ansa);
        ansb = findViewById(R.id.Ansb);
        ansc = findViewById(R.id.Ansc);
        ansa.setOnClickListener(this::onClick);
        ansb.setOnClickListener(this::onClick);
        ansc.setOnClickListener(this::onClick);
        totalque.setText("Total Question:" + totalQuestion);
        Loadnewque();
    }

    @Override
    public void onClick(View view) {


        Button clickbutton = (Button) view;
            // Correct answer
        if (clickbutton.getText().toString().equals((Qns.correctans[currentque])))
        {  clickbutton.setBackgroundColor(Color.GREEN);
            score++;
        } else
        {
            // Incorrect answer
            clickbutton.setBackgroundColor(Color.RED);

            // Find the button with the correct answer and highlight it in green
            if (ansa.getText().toString().equals((Qns.correctans[currentque]))) {
                ansa.setBackgroundColor(Color.GREEN);
            } else if (ansb.getText().toString().equals((Qns.correctans[currentque]))) {
                ansb.setBackgroundColor(Color.GREEN);
            } else if (ansc.getText().toString().equals((Qns.correctans[currentque]))) {
                ansc.setBackgroundColor(Color.GREEN);
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Reset button colors to default
                ansa.setBackgroundResource(R.drawable.buttonlay);
                ansb.setBackgroundResource(R.drawable.buttonlay);
                ansc.setBackgroundResource(R.drawable.buttonlay);

                // Move to the next question
                currentque++;

                if (currentque < totalQuestion) {
                    Loadnewque();
                } else {
                    // Quiz is finished
                    Finishquiz();
                }
            }
        }, 2000);
    }




    void Loadnewque() {
        if (currentque == totalQuestion) {
            Finishquiz();
            return;
        }
        que.setText(Qns.que[currentque]);
        ansa.setText(Qns.choice[currentque][0]);

        ansb.setText(Qns.choice[currentque][1]);

        ansc.setText(Qns.choice[currentque][2]);


    }

    private void Finishquiz() {
        String Result = "";
        if (score > totalQuestion * 0.40) {
            Result = "Passed";
        } else {
            Result = "Fail";
        }
        new AlertDialog.Builder(this)
                .setTitle(Result)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", ((dialog, i) -> restartQuiz()))
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentque = 0;
        Loadnewque();
        ansa.setBackgroundResource(R.drawable.buttonlay);
        ansb.setBackgroundResource(R.drawable.buttonlay);
        ansc.setBackgroundResource(R.drawable.buttonlay);
    }
}