package com.example.mealplanner.RandomMeal.View;

import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.Model.Meal.MealResponse;

import java.util.List;

public interface RandomMealView {

    void showError(String errorMessage);
    void navigateToMealActivity(Meal meal);
    void showMultiplesMeals(Meal meal);

}
