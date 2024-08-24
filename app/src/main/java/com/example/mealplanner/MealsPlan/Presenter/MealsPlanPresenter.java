package com.example.mealplanner.MealsPlan.Presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.mealplanner.DataBackup.FirebaseSyncManager;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Database.Model.MealDate.MealDate;
import com.example.mealplanner.FavouriteMeals.Presenter.SaveMealListener;
import com.example.mealplanner.MealsPlan.View.MealsPlanView;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Model.UserSession;

import java.util.ArrayList;
import java.util.List;

public class MealsPlanPresenter implements DataBaseDatedMealsListener, SaveMealListener, DatedMealsLoadedListener {

    private MealsPlanView view;
    private Repository repository;
    private List <LocalMeal> localMeals;
    FirebaseSyncManager firebaseSyncManager;

    public MealsPlanPresenter(MealsPlanView view, Repository repository) {
        this.view = view;
        this.repository = repository;
        this.localMeals = new ArrayList<>();
        firebaseSyncManager = FirebaseSyncManager.getInstance();
    }

    public void getMealsPlan(LifecycleOwner owner) {
        LiveData<List<MealDate>> favouriteMealsLiveData = repository.getDatedMealsByUserId(UserSession.getInstance().getUid());
        favouriteMealsLiveData.observe(owner, new Observer<List<MealDate>>() {
            @Override
            public void onChanged(List<MealDate> mealDates) {

                localMeals.clear();
                if(mealDates != null&&!mealDates.isEmpty())
                {
                    addMealPlansToLocal(mealDates, owner);
                }
                else
                {
                    view.displayMealsForDate(localMeals);
                }

            }
    });

    }

    private void addMealPlansToLocal(List<MealDate> mealDates, LifecycleOwner owner) {
        for (MealDate mealDate : mealDates) {
            repository.getMealById(mealDate.mealId).observe(owner, new Observer<LocalMeal>() {
                @Override
                public void onChanged(LocalMeal localMeal) {
                    if (localMeal != null) {
                        localMeal.setDate(mealDate.date);
                        localMeals.add(localMeal);
                        view.displayMealsForDate(localMeals);
                    }
                }
            });
        }
    }


    public void deleteMeal(LocalMeal meal) {

        repository.deleteMealDate(meal);
    }

    public void onMealCardClicked(LocalMeal meal) {
        view.navigateToMealDetails(meal);
    }

    public void onGuestBtnClicked() {
        view.navigateToLoginScreen();
    }

    public void onSyncClicked() {
        firebaseSyncManager.loadDatedMeals(this);
    }

    public void onBackupClicked() {
        repository.getDatedMealsByUserIdForFirebase(UserSession.getInstance().getUid(),this);
    }

    @Override
    public void onDatedMealsSuccess(List<MealDate> meals) {
        firebaseSyncManager.saveDatedMeals(meals,this);
    }

    @Override
    public void onDatedMealsLoadFailed(String errorMessage) {

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
    public void onMealsLoaded(List<MealDate> meals) {
        for(MealDate meal : meals){
            repository.insertMealDateForFirebase(meal);
        }
    }

    @Override
    public void onMealLoadFailed(String errorMessage) {

    }
}
