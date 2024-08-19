package com.example.mealplanner.RandomMeal.View;

import com.example.mealplanner.Model.Meal.MealResponse;

public interface RandomMealView {

    void showRandomMeal(MealResponse meal);
    void showError(String errorMessage);

}
