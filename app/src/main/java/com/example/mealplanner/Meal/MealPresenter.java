package com.example.mealplanner.Meal;

import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.NetworkListeners.MealDetailsNetworkListener;

public class MealPresenter implements MealDetailsNetworkListener {
    private MealView view;
    private Repository repository;

    public MealPresenter(MealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }
    public void fetchMealDetails(String mealId) {
        repository.getMealDetails(mealId, this);
    }

    @Override
    public void onMealDetailsSuccess(Meal meal) {
        view.displayMealDetails(meal);
    }

    @Override
    public void onMealDetailsFailure(String errorMessage) {

        view.showError(errorMessage);
    }
}
