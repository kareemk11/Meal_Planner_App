package com.example.mealplanner.MealsPlan.Presenter;

import com.example.mealplanner.Database.Model.MealDate.MealDate;

import java.util.List;

public interface DataBaseDatedMealsListener {

    void onDatedMealsSuccess(List<MealDate> meals);

    void onDatedMealsLoadFailed(String errorMessage);
}
