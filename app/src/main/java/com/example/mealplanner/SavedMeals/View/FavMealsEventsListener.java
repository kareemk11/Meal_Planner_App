package com.example.mealplanner.SavedMeals.View;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

public interface FavMealsEventsListener {
    void onCardClick(LocalMeal meal);

    void onRemoveButtonClick(LocalMeal meal);
}
