package com.example.mealplanner.MealsPlan;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

import java.util.List;

public interface MealsPlanView {
    void displayMealsForDate(List<LocalMeal> meals);
    void showError(String errorMessage);

    void onMealSaved(LocalMeal meal);
    void onMealDeleted(LocalMeal meal);
}
