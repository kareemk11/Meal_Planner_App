package com.example.mealplanner.ListedMeals.View;

import com.example.mealplanner.Model.Meal.Meal;

import java.util.List;

public interface SearchedMealsView {
    void showMeals(List<Meal> meals);

    void showError(String errorMessage);

    void navigateToMealActivity(Meal meal);
}
