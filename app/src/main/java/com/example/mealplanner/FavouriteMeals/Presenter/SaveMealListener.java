package com.example.mealplanner.FavouriteMeals.Presenter;

public interface SaveMealListener {

    void onSaveMealSuccess();

    void onSaveMealFailure(String errorMessage);

}
