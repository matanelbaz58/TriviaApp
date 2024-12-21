package com.example.triviaapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TriviaManager {
    private static final String TAG = "TriviaManager";
    private List<Question> questions;

    public TriviaManager(Context context, String fileName) {
        this.questions = loadQuestionsFromFile(context, fileName);
    }

    private List<Question> loadQuestionsFromFile(Context context, String fileName) {
        List<Question> questionList = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject questionObject = jsonArray.getJSONObject(i);
                String questionText = questionObject.getString("question");
                JSONArray optionsArray = questionObject.getJSONArray("options");
                List<String> optionsList = new ArrayList<>();
                for (int j = 0; j < optionsArray.length(); j++) {
                    optionsList.add(optionsArray.getString(j));
                }
                int correctAnswerIndex = questionObject.getInt("correctAnswerIndex");

                Question question = new Question(questionText, optionsList, correctAnswerIndex);
                questionList.add(question);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error loading questions from file", e);
        }
        return questionList;
    }

    public List<Question> getQuestions() {
        return questions;
}
}