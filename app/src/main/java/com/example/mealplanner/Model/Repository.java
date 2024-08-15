package com.example.mealplanner.Model;

import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.Network.NetworkListener;

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
    public void getRandomMeal(NetworkListener listener) {
        mealsRemoteDataScource.makeNetworkCall(listener);
    }

}
