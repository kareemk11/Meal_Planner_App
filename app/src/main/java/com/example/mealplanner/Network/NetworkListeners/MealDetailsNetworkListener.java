package com.example.mealplanner.Network.NetworkListeners;

import com.example.mealplanner.Network.Model.Meal.Meal;

public interface MealDetailsNetworkListener {
    void onMealDetailsSuccess(Meal meal);
    void onMealDetailsFailure(String errorMessage);
}
