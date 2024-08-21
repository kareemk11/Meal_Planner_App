package com.example.mealplanner.RandomMeal.Presenter;

import com.example.mealplanner.Network.Model.Meal.Meal;
import com.example.mealplanner.RandomMeal.View.RandomMealView;
import com.example.mealplanner.Network.Model.Meal.MealResponse;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.NetworkListeners.RandomMealNetworkListener;
import com.google.firebase.auth.FirebaseAuth;

public class RandomMealFragmentPresenter implements RandomMealNetworkListener {

    private Repository repository;
    private RandomMealView view;
    FirebaseAuth mAuth;

    public RandomMealFragmentPresenter(RandomMealView view , Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void getRandomMeal() {
        repository.getRandomMeal(this);
    }

    @Override
    public void onRandomMealSuccess(MealResponse meal) {
        view.showRandomMeal(meal);

    }

    @Override
    public void onRandomMealFailure(String errorMessage) {
        view.showError(errorMessage);

    }

    public void onCardClick(Meal meal) {

        view.navigateToMealActivity(meal);

    }







}
