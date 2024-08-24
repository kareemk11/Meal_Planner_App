package com.example.mealplanner.Database.Model.MealDate;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Database.Model.User.User;

@Entity(tableName = "meal_date",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = LocalMeal.class,
                        parentColumns = "mealId",
                        childColumns = "mealId",
                        onDelete = ForeignKey.CASCADE)
        })
public class MealDate {
    @PrimaryKey(autoGenerate = true)
    public int mealDateId;

    public String userId;
    public String mealId;
    public String date;


    public int getMealDateId() {
        return mealDateId;
    }

    public void setMealDateId(int mealDateId) {
        this.mealDateId = mealDateId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}