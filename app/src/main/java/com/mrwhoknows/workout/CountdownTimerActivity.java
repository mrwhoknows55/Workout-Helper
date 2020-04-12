package com.mrwhoknows.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrwhoknows.workout.model.Workout;

import java.util.ArrayList;
import java.util.Locale;

public class CountdownTimerActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private TextView setsRemainingText, countdownTimerText, currentExText, nextExText, restText;
    private ImageView currentImg, nextImg;

    private int sets;
    private boolean isTimerRunning, isBreak;
    private long timeLeftInMillis;
    private ArrayList<Workout> workoutArrayList;
    int pos;

    private Button pauseBtn;

    MediaPlayer mediaPlayer;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setsRemainingText = findViewById(R.id.setsRemaining);
        countdownTimerText = findViewById(R.id.countdownTimerText);
        currentExText = findViewById(R.id.currentExName);
        nextExText = findViewById(R.id.nextExName);
        restText = findViewById(R.id.restText);

        pauseBtn = findViewById(R.id.pauseBtn);

        currentImg = findViewById(R.id.currentExImg);
        nextImg = findViewById(R.id.nextExImg);

        if (getIntent().hasExtra("object") && getIntent().hasExtra("sets")) {
            this.workoutArrayList = getIntent().getParcelableArrayListExtra("object");
            this.sets = getIntent().getIntExtra("sets", 2) - 1;

            setsRemainingText.setText(String.valueOf(sets) + " Sets Remaining");

            pos = 0;
            changeExercises();
            startTimer();
        }

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pauseBtn.getText().toString().equals("Done")) {
                    finish();
                } else {
                    if (isTimerRunning) {
                        pauseTimer();
                        pauseBtn.setText("Restart");
                    } else {
                        startTimer();
                        pauseBtn.setText("Pause");
                    }
                }
            }
        });

    }

    private void pauseTimer() {

        countDownTimer.cancel();
        isTimerRunning = false;

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }

    }

    private void startTimer() {
        pauseBtn.setVisibility(View.VISIBLE);
        timeLeftInMillis = workoutArrayList.get(pos).getWorkoutTimeInSec() * 1000;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.audio1);
        mediaPlayer.start();
        mediaPlayer.setScreenOnWhilePlaying(true);

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                if (pos + 2 > workoutArrayList.size()) {

                    if (sets > 0) {
                        sets--;
                        setsRemainingText.setText(String.valueOf(sets) + " Sets Remaining");
                        isTimerRunning = true;
                        pos = 0;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        breakTimer();
                    } else {
                        isTimerRunning = false;
                        countdownTimerText.setText("Finished!");
                        mediaPlayer.stop();
                        pauseBtn.setVisibility(View.VISIBLE);
                        pauseBtn.setText("Done");
                        restText.setVisibility(View.INVISIBLE);
                        setsRemainingText.setVisibility(View.INVISIBLE);
                        currentImg.setVisibility(View.GONE);
                    }
                } else {
                    isTimerRunning = true;
                    pos++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    breakTimer();
                }
            }
        }.start();

        isTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdownTimerText.setText(timeLeftFormatted);
    }

    private void breakTimer() {
        long breakTime = 5000; //40000
        isBreak = true;

        mediaPlayer.stop();
        pauseBtn.setVisibility(View.INVISIBLE);
        restText.setText("Take Rest");

        changeExercises();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CountDownTimer countDownTimer = new CountDownTimer(breakTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                countdownTimerText.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                startTimer();
            }
        }.start();
    }

    private void changeExercises() {
        currentExText.setText("Current: " + workoutArrayList.get(pos).getWorkoutName());
        currentImg.setImageResource(workoutArrayList.get(pos).getWorkoutImage());
        if (pos + 1 < workoutArrayList.size()) {
            nextExText.setText("Next: " + workoutArrayList.get(pos + 1).getWorkoutName());
            nextImg.setImageResource(workoutArrayList.get(pos + 1).getWorkoutImage());
        } else {
            if (sets > 0) {
                nextExText.setText("Next: " + workoutArrayList.get(0).getWorkoutName());
                nextImg.setImageResource(workoutArrayList.get(0).getWorkoutImage());
            } else {
                nextExText.setText("No Exercises Left");
                nextImg.setVisibility(View.GONE);
            }
        }
    }

}
