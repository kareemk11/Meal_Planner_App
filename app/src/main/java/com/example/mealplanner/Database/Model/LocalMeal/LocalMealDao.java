package com.example.mealplanner.Database.Model.LocalMeal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LocalMealDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMeal(LocalMeal meal);


    @Query("SELECT * FROM meal WHERE mealId = :mealId")
    LiveData<LocalMeal> getMealById(String mealId);

    @Query("SELECT * FROM meal")
    LiveData<List<LocalMeal>> getAllMeals();


    @Update
    void updateMeal(LocalMeal meal);

    @Delete
    void deleteMeal(LocalMeal meal);
}
