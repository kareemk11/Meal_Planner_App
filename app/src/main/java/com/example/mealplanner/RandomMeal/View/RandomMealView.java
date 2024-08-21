package com.example.mealplanner.RandomMeal.View;

import com.example.mealplanner.Network.Model.Meal.Meal;
import com.example.mealplanner.Network.Model.Meal.MealResponse;

public interface RandomMealView {

    void showRandomMeal(MealResponse meal);
    void showError(String errorMessage);
    void navigateToMealActivity(Meal meal);

}
