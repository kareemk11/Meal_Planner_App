package com.example.mealplanner.Network.NetworkListeners.ListedMeals;

import com.example.mealplanner.Model.Meal.Meal;

import java.util.List;

public interface MealsByCategoryNetworkListener {
    void onMealsByCategorySuccess(List<Meal> meals);
    void onMealsByCategoryFailure(String errorMessage);
}
