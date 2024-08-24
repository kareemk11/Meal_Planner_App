package com.example.mealplanner.RandomMeal.Presenter;

import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.RandomMeal.View.RandomMealView;
import com.example.mealplanner.Model.Meal.MealResponse;
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






    @Override
    public void onRandomMealFailure(String errorMessage) {
        view.showError(errorMessage);

    }

    @Override
    public void onMealListSuccess(Meal meal) {
        view.showMultiplesMeals(meal);
    }

    public void onCardClick(Meal meal) {

        view.navigateToMealActivity(meal);

    }


    public void getMultiplesMeals() {
      repository.getMultiplesMeals(this);
    }

    public void onGuestBtnClicked() {
        view.navigateToLoginScreen();
    }
}
