package com.mrwhoknows.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.WorkSource;
import android.util.Log;
import android.widget.TextView;

import com.mrwhoknows.workout.adapter.WorkoutAdapter;
import com.mrwhoknows.workout.model.Workout;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity implements WorkoutAdapter.OnWorkoutListener {

    private static final String TAG = "CheckoutActivity";
    private ArrayList<Workout> workouts;
    private int sets;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView totalTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        totalTimeText = findViewById(R.id.totalTimeText);

        Intent intent = getIntent();

        if (intent.hasExtra("workout") && intent.hasExtra("sets")) {

            sets = intent.getIntExtra("sets", 1);
            workouts = intent.getParcelableArrayListExtra("workout");
            setRecyclerView();

            int totalTime = 0;
            for (Workout workout : workouts) {
                totalTime += workout.getWorkoutTimeInSec();
            }

            totalTimeText.setText("Total Time: " + String.valueOf(totalTime/60 * sets)+" Mins");
        } else {
            Log.d(TAG, "getIntent: error");
        }

    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new WorkoutAdapter(workouts, sets, CheckoutActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onWorkoutClick(int position) {
        Log.d(TAG, "onWorkoutClick: clicked");

    }
}
