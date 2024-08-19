package com.example.mealplanner.Model;

import com.example.mealplanner.Network.NetworkListeners.MealDetailsNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByAreaNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.AreaNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.IngredientsNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.CategoryNetworkListener;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByCategoryNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByMainIngredientNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.RandomMealNetworkListener;

public class Repository {
    private static Repository instance;
    private MealsRemoteDataScource mealsRemoteDataScource;


    private Repository(MealsRemoteDataScource mealsRemoteDataScourcec) {
        this.mealsRemoteDataScource = mealsRemoteDataScourcec;
    }

    public static Repository getInstance(MealsRemoteDataScource mealsRemoteDataScource) {
        if (instance == null) {
            instance = new Repository(mealsRemoteDataScource);
        }
        return instance;
    }
    public void getRandomMeal(RandomMealNetworkListener listener) {
        mealsRemoteDataScource.getRandomMeal(listener);
    }
    public void getCategories(CategoryNetworkListener listener) {
        mealsRemoteDataScource.getCategories(listener);
    }
    public void getAreas(AreaNetworkListener listener) {
        mealsRemoteDataScource.getAreas(listener);
    }
    public void getIngredients(IngredientsNetworkListener listener) {
        mealsRemoteDataScource.getIngredients(listener);
    }
    public void getMealsByCategory(String category, MealsByCategoryNetworkListener listener) {
        mealsRemoteDataScource.getMealsByCategory(category,listener);
    }
    public void getMealsByArea(String area, MealsByAreaNetworkListener listener) {
        mealsRemoteDataScource.getMealsByArea(area,listener);
    }
    public void getMealsByIngredient(String ingredient, MealsByMainIngredientNetworkListener listener) {
        mealsRemoteDataScource.getMealsByMainIngredient(ingredient,listener);
    }
    public void getMealDetails(String mealId, MealDetailsNetworkListener listener) {
        mealsRemoteDataScource.getMealDetails(mealId,listener);
    }

}
