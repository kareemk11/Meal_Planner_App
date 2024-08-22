package com.example.mealplanner.FavouriteMeals.View;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

public interface FavouriteMealsEventsListener {
    void onCardClick(LocalMeal meal);

    void onRemoveButtonClick(LocalMeal meal);
}
