package com.mrwhoknows.workout.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Workout implements Parcelable {

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };
    private String workoutName;
    private int workoutTimeInSec;
    private int workoutImage;

    public Workout(String workoutName, int workoutTimeInSec, int workoutImage) {
        this.workoutName = workoutName;
        this.workoutTimeInSec = workoutTimeInSec;
        this.workoutImage = workoutImage;
    }

    protected Workout(Parcel in) {
        workoutName = in.readString();
        workoutTimeInSec = in.readInt();
        workoutImage = in.readInt();
    }

    public Workout() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workoutName);
        dest.writeInt(workoutTimeInSec);
        dest.writeInt(workoutImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getWorkoutImage() {
        return workoutImage;
    }

    public void setWorkoutImage(int workoutImage) {
        this.workoutImage = workoutImage;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getWorkoutTimeInSec() {
        return workoutTimeInSec;
    }

    public void setWorkoutTimeInSec(int workoutTimeInSec) {
        this.workoutTimeInSec = workoutTimeInSec;
    }
}