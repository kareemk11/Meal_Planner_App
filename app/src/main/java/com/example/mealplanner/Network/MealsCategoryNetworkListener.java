package com.example.mealplanner.Network;

import com.example.mealplanner.Model.Category.Category;

import java.util.List;

public interface MealsCategoryNetworkListener {

    void onMealsCategorySuccess(List<Category> meals);

    void onMealsCategoryFailure(String message);
}
