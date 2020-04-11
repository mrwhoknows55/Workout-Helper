package com.mrwhoknows.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mrwhoknows.workout.model.Workout;

import java.util.ArrayList;
import java.util.Locale;

public class CountdownTimerActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private TextView setsRemainingText, countdownTimerText, currentExText, nextExText;

    private int sets;
    private boolean isTimerRunning, isBreak;
    private long timeLeftInMillis;
    private ArrayList<Workout> workoutArrayList;
    int pos;

    private Button pauseBtn;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        setsRemainingText = findViewById(R.id.setsRemaining);
        countdownTimerText = findViewById(R.id.countdownTimerText);
        currentExText = findViewById(R.id.currentExName);
        nextExText = findViewById(R.id.nextExName);

        pauseBtn = findViewById(R.id.pauseBtn);

        if (getIntent().hasExtra("object") && getIntent().hasExtra("sets")) {
            this.workoutArrayList = getIntent().getParcelableArrayListExtra("object");
            this.sets = getIntent().getIntExtra("sets", 2) - 1;

            setsRemainingText.setText(String.valueOf(sets) + " Sets Remaining");

            pos = 0;
            currentExText.setText(workoutArrayList.get(pos).getWorkoutName());
            if (pos + 1 < workoutArrayList.size())
                nextExText.setText(workoutArrayList.get(pos + 1).getWorkoutName());
            else {
                if (sets >= 0)
                    nextExText.setText(workoutArrayList.get(0).getWorkoutName());
                else
                    nextExText.setText("No Exercises Left");
            }
            startTimer();
        }

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    pauseTimer();
                    pauseBtn.setText("Restart");
                } else {
                    startTimer();
                    pauseBtn.setText("Pause");
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
                        pauseBtn.setVisibility(View.INVISIBLE);
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

        currentExText.setText(workoutArrayList.get(pos).getWorkoutName());
        if (pos + 1 < workoutArrayList.size())
            nextExText.setText(workoutArrayList.get(pos + 1).getWorkoutName());
        else {
            if (sets > 0)
                nextExText.setText(workoutArrayList.get(0).getWorkoutName());
            else
                nextExText.setText("No Exercises Left");
        }

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

}
