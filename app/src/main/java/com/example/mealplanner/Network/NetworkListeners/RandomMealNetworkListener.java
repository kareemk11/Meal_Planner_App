package com.example.mealplanner.Network.NetworkListeners;

import com.example.mealplanner.Model.Category.CategoryResponse;
import com.example.mealplanner.Model.Meal.MealResponse;

import java.util.List;

public interface RandomMealNetworkListener {

    void onRandomMealSuccess(MealResponse meal);
    void onRandomMealFailure(String errorMessage);
    void onSearchMealsSuccess(List<MealResponse> meals);
    void onSearchMealsFailure(String errorMessage);
    void onGetMealsByFirstLetterSuccess(List<MealResponse> meals);
    void onGetMealsByFirstLetterFailure(String errorMessage);
    void onGetCategoriesSuccess(List<CategoryResponse> categories);
    void onGetCategoriesFailure(String errorMessage);
    void onGetMealsByMainIngredientSuccess(List<MealResponse> meals);
    void onGetMealsByMainIngredientFailure(String errorMessage);
    void onGetMealsByCategorySuccess(List<MealResponse> meals);
    void onGetMealsByCategoryFailure(String errorMessage);
    void onGetMealsByAreaSuccess(List<MealResponse> meals);
    void onGetMealsByAreaFailure(String errorMessage);
    void onGetMealDetailsSuccess(MealResponse meal);
    void onGetMealDetailsFailure(String errorMessage);
}
