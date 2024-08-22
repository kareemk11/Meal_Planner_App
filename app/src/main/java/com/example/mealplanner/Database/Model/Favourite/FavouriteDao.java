package com.example.mealplanner.Database.Model.Favourite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertFavorite(FavouriteMeal favorite);

    // Get all favorite meals for a specific user
    @Query("SELECT * FROM favorite WHERE userId = :userId")
    LiveData<List<FavouriteMeal>> getFavoritesByUserId(String userId);

    @Query("SELECT * FROM favorite WHERE mealId = :mealId AND userId = :userId")
    FavouriteMeal getFavoriteByMealIdAndUserId(String mealId, String userId);

    // Delete a favorite entry
    @Delete
    void deleteFavorite(FavouriteMeal favorite);

    @Query("DELETE FROM favorite WHERE mealId = :mealId")
    void deleteFavoriteByMealId(String mealId);
}

