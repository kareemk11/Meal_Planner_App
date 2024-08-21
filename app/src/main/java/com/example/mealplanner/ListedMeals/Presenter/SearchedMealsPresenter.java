package com.example.mealplanner.ListedMeals.Presenter;


import com.example.mealplanner.ListedMeals.View.SearchedMealsView;
import com.example.mealplanner.Network.Model.Meal.Meal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByAreaNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByCategoryNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByMainIngredientNetworkListener;

import java.util.List;

public class SearchedMealsPresenter implements
        MealsByAreaNetworkListener, MealsByMainIngredientNetworkListener, MealsByCategoryNetworkListener {
    private Repository repository;
    private SearchedMealsView view;


    public SearchedMealsPresenter(Repository repository, SearchedMealsView view) {
        this.repository = repository;
        this.view = view;
    }

    public void  searchMealsByArea(String query) {
        repository.getMealsByArea(query, this);
    }
    public void  searchMealsByCategory(String query) {
        repository.getMealsByCategory(query, this);
    }

    public void  searchMealsByIngredient(String query) {
        repository.getMealsByIngredient(query, this);
    }

    @Override
    public void onMealsByAreaSuccess(List<Meal> meals) {
        view.showMeals(meals);
    }

    @Override
    public void onMealsByAreaFailure(String errorMessage) {
        view.showError(errorMessage);
    }

    @Override
    public void onMealsByMainIngredientSuccess(List<Meal> meals) {
        view.showMeals(meals);
    }

    @Override
    public void onMealsByMainIngredientFailure(String message) {

        view.showError(message);
    }



    @Override
    public void onMealsByCategorySuccess(List<Meal> meals) {
        view.showMeals(meals);
    }

    @Override
    public void onMealsByCategoryFailure(String errorMessage) {

        view.showError(errorMessage);
    }

    public void onCardClick(Meal meal) {

        view.navigateToMealActivity(meal);

    }
}



