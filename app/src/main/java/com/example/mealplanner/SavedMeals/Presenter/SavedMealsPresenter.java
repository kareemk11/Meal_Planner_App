package com.example.mealplanner.SavedMeals.Presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.SavedMeals.View.SavedMealsView;

import java.util.ArrayList;
import java.util.List;

public class SavedMealsPresenter {

    private static final String TAG = "SavedMealsPresenter";
    private SavedMealsView view;
    private Repository repository;
    private List<LocalMeal> localMeals;

    public SavedMealsPresenter(SavedMealsView view, Repository repository) {
        this.view = view;
        this.repository = repository;
        this.localMeals = new ArrayList<>();
    }

    public void getSavedMeals(LifecycleOwner owner) {

        LiveData<List<FavouriteMeal>> favouriteMealsLiveData = repository.getSavedMeals(UserSession.getInstance().getUid());

        favouriteMealsLiveData.observe(owner, new Observer<List<FavouriteMeal>>() {
            @Override
            public void onChanged(List<FavouriteMeal> meals) {
                localMeals.clear();
                if (meals != null && !meals.isEmpty()) {
                    addFavToLocal(meals);
                }
                else {
                    view.displayMeals(localMeals);
                }
            }
        });
    }

    private void addFavToLocal(List<FavouriteMeal> meals) {
        for (FavouriteMeal meal : meals) {
            repository.getMealById(meal.mealId).observeForever(new Observer<LocalMeal>() {
                @Override
                public void onChanged(LocalMeal localMeal) {
                    if (localMeal != null) {
                        localMeals.add(localMeal);
                        view.displayMeals(localMeals);
                    }
                }
            });
        }
    }

    public void deleteMeal(LocalMeal meal) {
        repository.deleteFavorite(meal);
    }

    public void onMealCardClicked(LocalMeal meal) {
        view.navigateToMealDetails(meal);
    }
}
