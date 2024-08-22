package com.example.mealplanner.Meal.View;

import com.example.mealplanner.Network.Model.Meal.Meal;

public interface MealView {
    void displayMealDetails(Meal meal);

    void showError(String errorMessage);

    void setMealAddedToFavourites(boolean isFavourite);

    void setMealAddedToPlan(boolean isAdded);
}
