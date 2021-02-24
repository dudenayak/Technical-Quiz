package com.example.technicalquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ= 1;
    public static final String SHARED_PREFERENCE="sharedpreference";
    public static final String KEY_HIGHSCORE="keyHighscore";
    private TextView tvhighscore;
    private  int highscore;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.startquiz);
        tvhighscore=findViewById(R.id.highscore);
        loadHighscore();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,QuestionActivity.class);
                startActivityForResult(i,REQUEST_CODE_QUIZ);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuestionActivity.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }
    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        tvhighscore.setText("Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        tvhighscore.setText("Highscore: " + highscore);
    }
}
