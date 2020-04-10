package com.mrwhoknows.workout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mrwhoknows.workout.model.Workout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextInputLayout exCountText, setCountText;

    private String exCount, setCount;

    private ArrayList<Workout> workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exCountText = findViewById(R.id.exerciseCount);
        setCountText = findViewById(R.id.setCount);

    }

    public void selectiveExMode(View view) {
        Log.d(TAG, "selectiveExMode: selected");

        exCount = exCountText.getEditText().getText().toString();
        setCount = setCountText.getEditText().getText().toString();

        if (exCount.equals("") && setCount.equals("")) {
            exCountText.setError("Required");
            setCountText.setError("Required");

        } else if (exCount.equals("") || exCount.equals("0")) {
            exCountText.setError("Required");
            setCountText.setError(null);

        } else if (setCount.equals("") || setCount.equals("0")) {
            setCountText.setError("Required");
            exCountText.setError(null);
        } else {
            setCountText.setError(null);
            exCountText.setError(null);

            setWorkoutList();

            ArrayList<Workout> selectedExercises = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(exCount); i++) {
                selectedExercises.add(workouts.get(i));
            }

            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putParcelableArrayListExtra("workout", selectedExercises);
            intent.putExtra("sets", Integer.parseInt(setCount));
            startActivity(intent);
        }

    }

    public void randomExMode(View view) {
        Log.d(TAG, "randomExMode: selected");

        setCount = setCountText.getEditText().getText().toString();

        if (setCount.equals("")) {
            setCountText.setError("Required");
            exCountText.setError(null);
        } else {
            setCountText.setError(null);
            exCountText.setError(null);

            ArrayList<Workout> randomExercises = new ArrayList<>();

            Random ran = new Random();
            int rand = ran.nextInt(13) + 1;
            setWorkoutList();
            for (int i = 0; i < rand; i++) {
                randomExercises.add(workouts.get(i));
            }

            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putParcelableArrayListExtra("workout", randomExercises);
            intent.putExtra("sets", Integer.parseInt(setCount));
            startActivity(intent);

        }

    }

    private void setWorkoutList() {
        workouts = new ArrayList<>();

        workouts.add(new Workout("Pushups", 240, R.drawable.img01));
        workouts.add(new Workout("Situps", 180, R.drawable.img02));
        workouts.add(new Workout("THREE", 180, R.drawable.img03));
        workouts.add(new Workout("Side Bends", 240, R.drawable.img04));
        workouts.add(new Workout("Leg Lifts", 60, R.drawable.img05));
        workouts.add(new Workout("Weighted Push Ups", 120, R.drawable.img06));
        workouts.add(new Workout("Bicep Dumbbell Curl", 120, R.drawable.img07));
        workouts.add(new Workout("EIGHT", 180, R.drawable.img08));
        workouts.add(new Workout("NINE", 180, R.drawable.img09));
        workouts.add(new Workout("Sited Rows", 120, R.drawable.img10));
        workouts.add(new Workout("Incline Bench Press", 180, R.drawable.img11));
        workouts.add(new Workout("Bench Press", 120, R.drawable.img12));
        workouts.add(new Workout("THIRTEEN", 180, R.drawable.img13));
        workouts.add(new Workout("Incline Crunches", 180, R.drawable.img14));

        Collections.shuffle(workouts);
    }

}
