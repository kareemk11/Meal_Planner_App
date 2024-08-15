package com.example.mealplanner.HomeActivity.RandomMeal;

import com.example.mealplanner.Model.Meal.MealResponse;

public interface RandomMealView {

    void showRandomMeal(MealResponse meal);
    void showError(String errorMessage);

    void finish(
    );
}
