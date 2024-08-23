package com.example.mealplanner.Network.NetworkListeners;

import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.Model.Meal.MealResponse;

public interface RandomMealNetworkListener {

    void onRandomMealFailure(String errorMessage);
    void onMealListSuccess(Meal meal);
}
