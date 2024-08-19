package com.example.mealplanner.Network.NetworkListeners;

import com.example.mealplanner.Model.Category.CategoryResponse;
import com.example.mealplanner.Model.Meal.MealResponse;

import java.util.List;

public interface RandomMealNetworkListener {

    void onRandomMealSuccess(MealResponse meal);
    void onRandomMealFailure(String errorMessage);
}
