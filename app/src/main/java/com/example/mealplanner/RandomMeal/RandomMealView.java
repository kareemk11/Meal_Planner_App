package com.example.mealplanner.RandomMeal;

import com.example.mealplanner.Model.Meal;
import com.example.mealplanner.Model.MealResponse;

public interface RandomMealView {

    void showRandomMeal(MealResponse meal);
    void showError(String errorMessage);

    void finish(
    );
}
