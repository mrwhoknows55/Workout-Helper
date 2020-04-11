package com.mrwhoknows.workout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrwhoknows.workout.R;
import com.mrwhoknows.workout.model.Workout;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutHolder> {

    private ArrayList<Workout> workouts;
    private OnWorkoutListener onWorkoutListener;
    private int sets;

    public WorkoutAdapter(ArrayList<Workout> workouts, int sets, OnWorkoutListener onWorkoutListener) {
        this.workouts = workouts;
        this.sets = sets;
        this.onWorkoutListener = onWorkoutListener;
    }

    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_card_item, parent, false);

        return new WorkoutHolder(view, onWorkoutListener);

    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder holder, int position) {

        holder.time.setText(String.valueOf(workouts.get(position).getWorkoutTimeInSec() / 60) + ":" + String.valueOf(workouts.get(position).getWorkoutTimeInSec() % 60) + " Mins");
        holder.set.setText(String.valueOf(sets) + " Sets");
        holder.image.setImageResource(workouts.get(position).getWorkoutImage());
        holder.name.setText(workouts.get(position).getWorkoutName());

    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public interface OnWorkoutListener {
        void onWorkoutClick(int position);
    }

    public static class WorkoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView set, time, name;
        ImageView image;
        OnWorkoutListener onWorkoutListener;

        public WorkoutHolder(@NonNull View itemView, OnWorkoutListener onWorkoutListener) {
            super(itemView);

            this.onWorkoutListener = onWorkoutListener;

            set = itemView.findViewById(R.id.cardSetCount);
            time = itemView.findViewById(R.id.cardExTime);
            name = itemView.findViewById(R.id.cardExName);
            image = itemView.findViewById(R.id.cardExImg);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onWorkoutListener.onWorkoutClick(getAdapterPosition());
        }
    }

}
