package com.example.mealplanner.SavedMeals;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

public interface LocalMealListener {
    void onMealSaved(LocalMeal meal);
    void onMealDeleted(LocalMeal meal);

}
