package com.example.mealplanner.Network.NetworkListeners.ListedMeals;

import com.example.mealplanner.Model.Meal.Meal;

import java.util.List;

public interface MealsByAreaNetworkListener {
    void onMealsByAreaSuccess(List<Meal> meals);
    void onMealsByAreaFailure(String errorMessage);
}
