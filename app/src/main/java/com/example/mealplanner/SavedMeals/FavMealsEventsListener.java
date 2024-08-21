package com.example.mealplanner.SavedMeals;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Network.Model.Meal.Meal;

public interface FavMealsEventsListener {
    void onCardClick(LocalMeal meal);

    void onRemoveButtonClick(LocalMeal meal);
}
