package com.example.mealplanner.SavedMeals;

import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

import java.util.List;

public interface SavedMealsView {
    void displayMeals(List<LocalMeal> meals);
    void showError(String errorMessage);
}
