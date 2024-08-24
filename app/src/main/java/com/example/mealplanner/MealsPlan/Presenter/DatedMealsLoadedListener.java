package com.example.mealplanner.MealsPlan.Presenter;

import com.example.mealplanner.Database.Model.MealDate.MealDate;

import java.util.List;

public interface DatedMealsLoadedListener {

    void onMealsLoaded(List<MealDate> meals);
    void onMealLoadFailed(String errorMessage);

}
