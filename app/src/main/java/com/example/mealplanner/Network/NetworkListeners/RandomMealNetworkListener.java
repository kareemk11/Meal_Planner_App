package com.example.mealplanner.Network.NetworkListeners;

import com.example.mealplanner.Network.Model.Meal.MealResponse;

public interface RandomMealNetworkListener {

    void onRandomMealSuccess(MealResponse meal);
    void onRandomMealFailure(String errorMessage);
}
