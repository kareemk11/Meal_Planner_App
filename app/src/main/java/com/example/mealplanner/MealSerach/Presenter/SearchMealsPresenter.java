package com.example.mealplanner.MealSerach.Presenter;

import com.example.mealplanner.MealSerach.View.SearchMealsView;
import com.example.mealplanner.Model.Area.Area;
import com.example.mealplanner.Model.Category.Category;
import com.example.mealplanner.Model.Ingredient.Ingredient;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.IngredientsNetworkListener;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.AreaNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.CategoryNetworkListener;

import java.util.List;

public class SearchMealsPresenter implements CategoryNetworkListener, AreaNetworkListener,
        IngredientsNetworkListener {

    private Repository repository;
    private SearchMealsView view;

    public SearchMealsPresenter(Repository repository, SearchMealsView view) {
        this.repository = repository;
        this.view = view;
    }
    public void getCategories() {
        repository.getCategories(this);

    }
    public void getAreas()
    {
        repository.getAreas(this);
    }
    public void getIngredients()
    {
        repository.getIngredients(this);
    }

    @Override
    public void onMealsCategorySuccess(List<Category> categories) {
        view.showCategories(categories);
    }

    @Override
    public void onMealsCategoryFailure(String message) {

        view.showError(message);
    }



    @Override
    public void onAreaSuccess(List<Area> areas) {
        view.showAreas(areas);
    }

    @Override
    public void onAreaFailure(String message) {
        view.showError(message);

    }


    @Override
    public void onIngredientsSuccess(List<Ingredient> ingredients) {
        view.showIngredients(ingredients);
    }

    @Override
    public void onIngredientsFailure(String message) {

        view.showError(message);
    }

    public void itemGotSelected(String type, String item){
        if(type.equals("area"))
        {
            view.navigateToListedMeals(type,item);
        }
        else if(type.equals("ingredient"))
        {
            view.navigateToListedMeals(type,item);
        }
        else if(type.equals("category"))
        {
            view.navigateToListedMeals(type,item);
        }
    }

}
