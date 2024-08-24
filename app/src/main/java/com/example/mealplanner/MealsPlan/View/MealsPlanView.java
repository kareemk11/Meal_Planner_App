package com.example.mealplanner.MealsPlan.View;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

import java.util.List;

public interface MealsPlanView {
    void displayMealsForDate(List<LocalMeal> meals);
    void showError(String errorMessage);

    void navigateToMealDetails(LocalMeal meal);

    void navigateToLoginScreen();

    void displayToastSuccess();
    void displayToastError(String message);
}
