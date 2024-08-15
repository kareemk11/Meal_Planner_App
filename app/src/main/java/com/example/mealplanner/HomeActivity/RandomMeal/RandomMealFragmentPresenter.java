package com.example.mealplanner.HomeActivity.RandomMeal;

import com.example.mealplanner.Model.Category.CategoryResponse;
import com.example.mealplanner.Model.Meal.MealResponse;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.NetworkListeners.RandomMealNetworkListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

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
    public void Logout() {



        view.finish();

    }

    @Override
    public void onSearchMealsSuccess(List<MealResponse> meals) {

    }

    @Override
    public void onSearchMealsFailure(String errorMessage) {

    }

    @Override
    public void onGetMealsByFirstLetterSuccess(List<MealResponse> meals) {

    }

    @Override
    public void onGetMealsByFirstLetterFailure(String errorMessage) {

    }

    @Override
    public void onGetCategoriesSuccess(List<CategoryResponse> categories) {

    }

    @Override
    public void onGetCategoriesFailure(String errorMessage) {

    }

    @Override
    public void onGetMealsByMainIngredientSuccess(List<MealResponse> meals) {

    }

    @Override
    public void onGetMealsByMainIngredientFailure(String errorMessage) {

    }

    @Override
    public void onGetMealsByCategorySuccess(List<MealResponse> meals) {

    }

    @Override
    public void onGetMealsByCategoryFailure(String errorMessage) {

    }

    @Override
    public void onGetMealsByAreaSuccess(List<MealResponse> meals) {

    }

    @Override
    public void onGetMealsByAreaFailure(String errorMessage) {

    }

    @Override
    public void onGetMealDetailsSuccess(MealResponse meal) {

    }

    @Override
    public void onGetMealDetailsFailure(String errorMessage) {

    }


}
