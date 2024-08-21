package com.example.mealplanner.Network.NetworkListeners.ListedMeals;

import com.example.mealplanner.Network.Model.Meal.Meal;

import java.util.List;

public interface MealsByMainIngredientNetworkListener {
   public void onMealsByMainIngredientSuccess(List<Meal> meals);
   public void onMealsByMainIngredientFailure(String message);
}
