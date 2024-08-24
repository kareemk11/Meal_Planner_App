package com.example.mealplanner.FavouriteMeals.Presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mealplanner.DataBackup.FirebaseSyncManager;
import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.FavouriteMeals.View.FavouriteMealsView;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMealsPresenter implements SaveMealListener, DatabaseEventListener,MealLoadListener {

    private static final String TAG = "SavedMealsPresenter";
    private FavouriteMealsView view;
    private Repository repository;
    private List<LocalMeal> localMeals;
    FirebaseSyncManager firebaseSyncManager;

    public FavouriteMealsPresenter(FavouriteMealsView view, Repository repository) {
        this.view = view;
        this.repository = repository;
        this.localMeals = new ArrayList<>();
        firebaseSyncManager = FirebaseSyncManager.getInstance();
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

    public void onGuestBtnClicked() {
        view.navigateToLoginScreen();
    }

    public void onBackupClicked() {
        Log.i(TAG, "onBackupClicked: ");
         repository.getFavouriteMealsByUserId(UserSession.getInstance().getUid(), this);

    }

    private void backUpData(List<FavouriteMeal> favouriteMeals) {
        Log.i(TAG, "backUpData: "+favouriteMeals);
        firebaseSyncManager.saveFavoriteMeals(favouriteMeals, this);
    }

    public void onSyncClicked() {
        firebaseSyncManager.loadMeals(this);
    }

    @Override
    public void onSaveMealSuccess() {
        view.displayToastSuccess();
    }

    @Override
    public void onSaveMealFailure(String errorMessage) {

        view.displayToastError(errorMessage);
    }

    @Override
    public void onDataSuccess(List<FavouriteMeal> meals) {
        backUpData(meals);
    }

    @Override
    public void onDataFailure(String errorMessage) {

    }

    @Override
    public void onMealsLoaded(List<FavouriteMeal> meals) {
        for(FavouriteMeal meal : meals)
        {
            repository.insertFavoriteMealFromFirebase(meal);
        }

    }

    @Override
    public void onMealLoadFailed(String errorMessage) {

    }
}
