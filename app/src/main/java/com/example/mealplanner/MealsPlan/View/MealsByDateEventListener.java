package com.example.mealplanner.MealsPlan.View;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

public interface MealsByDateEventListener {
    void onCardClick(LocalMeal meal);

    void onRemoveButtonClick(LocalMeal meal);
}
