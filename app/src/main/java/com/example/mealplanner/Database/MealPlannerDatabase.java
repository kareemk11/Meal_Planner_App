package com.example.mealplanner.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealplanner.Database.Model.Favourite.FavouriteDao;
import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.LocalIngredient.IngredientDao;
import com.example.mealplanner.Database.Model.LocalIngredient.LocalIngredient;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMealDao;
import com.example.mealplanner.Database.Model.MealDate.MealDate;
import com.example.mealplanner.Database.Model.MealDate.MealDateDao;
import com.example.mealplanner.Database.Model.User.User;
import com.example.mealplanner.Database.Model.User.UserDao;

@Database(entities = {User.class, LocalMeal.class, FavouriteMeal.class, MealDate.class, LocalIngredient.class}, version = 3)
public abstract class MealPlannerDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract LocalMealDao localMealDao();
    public abstract FavouriteDao favouriteDao();
    public abstract MealDateDao mealDateDao();
    public abstract IngredientDao ingredientDao();

    private static MealPlannerDatabase instance;

    public static MealPlannerDatabase getInstance(Context context) {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MealPlannerDatabase.class,"meal_planner_database").build();
        }
        return instance;
    }
}
