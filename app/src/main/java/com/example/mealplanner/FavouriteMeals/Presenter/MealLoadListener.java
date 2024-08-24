package com.example.mealplanner.FavouriteMeals.Presenter;

import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Model.Meal.Meal;

import java.util.List;

public interface MealLoadListener {
    void onMealsLoaded(List<FavouriteMeal> meals);
    void onMealLoadFailed(String errorMessage);

}
