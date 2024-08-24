package com.example.mealplanner.FavouriteMeals.View;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

import java.util.List;

public interface FavouriteMealsView {
    void displayMeals(List<LocalMeal> meals);
    void showError(String errorMessage);

    void navigateToMealDetails(LocalMeal meal);

    void navigateToLoginScreen();

    void displayToastSuccess();
    void displayToastError(String message);
}
