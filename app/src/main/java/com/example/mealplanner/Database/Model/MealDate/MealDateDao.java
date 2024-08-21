package com.example.mealplanner.Database.Model.MealDate;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDateDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMealDate(MealDate mealDate);


    @Query("SELECT * FROM meal_date WHERE userId = :userId AND date = :date")
    LiveData<List<MealDate>> getMealsByDate(String userId, String date);

    @Query("SELECT * FROM meal_date WHERE userId = :userId")
    LiveData<List<MealDate>> getDatedMealsByUserId(String userId);


    @Delete
    void deleteMealDate(MealDate mealDate);

    @Query("DELETE FROM meal_date WHERE mealId = :mealId")
    void deleteMealDateByMealId(String mealId);

}

