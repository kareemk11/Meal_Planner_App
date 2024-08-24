package com.example.mealplanner.Database.Model.Favourite;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Database.Model.User.User;

@Entity(tableName = "favorite",
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
public class FavouriteMeal {
    @PrimaryKey(autoGenerate = true)
    public int favoriteId;

    public String userId;
    public String mealId;


    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
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
}