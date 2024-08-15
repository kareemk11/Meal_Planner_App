package com.example.mealplanner.HomeActivity.MealSerach;

import com.example.mealplanner.Model.Area.Area;
import com.example.mealplanner.Model.Category.Category;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.AreaNetworkListener;
import com.example.mealplanner.Network.MealsCategoryNetworkListener;

import java.util.List;

public class SearchMealsPresenter implements MealsCategoryNetworkListener, AreaNetworkListener {

    private Repository repository;
    private SearchMealsView view;

    public SearchMealsPresenter(Repository repository, SearchMealsView view) {
        this.repository = repository;
        this.view = view;
    }
    public void getCategories() {
        repository.getCategories(this);

    }

    @Override
    public void onMealsCategorySuccess(List<Category> categories) {
        view.showCategories(categories);
    }

    @Override
    public void onMealsCategoryFailure(String message) {

        view.showError(message);
    }

    public void getAreas() {
        repository.getAreas(this);
    }

    @Override
    public void onAreaSuccess(List<Area> areas) {
        view.showAreas(areas);
    }

    @Override
    public void onAreaFailure(String message) {
        view.showError(message);

    }
    public void Logout() {



        view.finish();

    }
}
