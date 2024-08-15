package com.example.mealplanner.Model;

import com.example.mealplanner.Network.AreaNetworkListener;
import com.example.mealplanner.Network.MealsCategoryNetworkListener;
import com.example.mealplanner.Network.MealsRemoteDataScource;
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
    public void getCategories(MealsCategoryNetworkListener listener) {
        mealsRemoteDataScource.getCategories(listener);
    }
    public void getAreas(AreaNetworkListener listener) {
        mealsRemoteDataScource.getAreas(listener);
    }

}
