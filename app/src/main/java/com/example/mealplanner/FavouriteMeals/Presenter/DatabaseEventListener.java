package com.example.mealplanner.FavouriteMeals.Presenter;

import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;

import java.util.List;

public interface DatabaseEventListener {

    void onDataSuccess(List<FavouriteMeal> meals);

    void onDataFailure(String errorMessage);
}
