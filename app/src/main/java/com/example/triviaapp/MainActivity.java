package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;

    private TriviaManager triviaManager;
    private List<Question> questionList;

    private int currentQuestionIndex = 0;
    private int score = 0;

    // Constants
    private static final int TOTAL_QUESTIONS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuestion = findViewById(R.id.tvQuestion);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);

        triviaManager = new TriviaManager(this, "questions.json");
        questionList = triviaManager.getQuestions();

        displayQuestion(currentQuestionIndex);

        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswerSelected(0);
            }
        });

        btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswerSelected(1);
            }
        });

        btnOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswerSelected(2);
            }
        });

        btnOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAnswerSelected(3);
            }
        });
    }

    private void displayQuestion(int index) {
        if (questionList == null || questionList.isEmpty()) {
            tvQuestion.setText("No questions available.");
            return;
        }

        Question question = questionList.get(index);
        tvQuestion.setText(question.getQuestionText());
        btnOption1.setText(question.getOptions().get(0));
        btnOption2.setText(question.getOptions().get(1));
        btnOption3.setText(question.getOptions().get(2));
        btnOption4.setText(question.getOptions().get(3));
    }

    private void handleAnswerSelected(int selectedIndex) {
        // Check if the answer is correct
        Question currentQuestion = questionList.get(currentQuestionIndex);
        if (selectedIndex == currentQuestion.getCorrectAnswerIndex()) {
            score++;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex == TOTAL_QUESTIONS) {
            showResults();
        } else {
            displayQuestion(currentQuestionIndex);
        }
    }

    private void showResults() {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", TOTAL_QUESTIONS);
        startActivity(intent);
        finish();
    }
}