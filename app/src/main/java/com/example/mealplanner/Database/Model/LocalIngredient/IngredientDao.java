package com.example.mealplanner.Database.Model.LocalIngredient;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {

    // Insert a new ingredient
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertIngredient(LocalIngredient ingredient);

    // Get ingredients by mealId
    @Query("SELECT * FROM ingredient WHERE mealId = :mealId")
    LiveData<List<LocalIngredient>> getIngredientsByMealId(long mealId);

    // Update ingredient details
    @Update
    void updateIngredient(LocalIngredient ingredient);

    // Delete an ingredient
    @Delete
    void deleteIngredient(LocalIngredient ingredient);
}
