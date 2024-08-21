package com.example.mealplanner.Meal;

import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Database.Model.MealDate.MealDate;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.Network.Model.Meal.IngredientOfMeal;
import com.example.mealplanner.Network.Model.Meal.Meal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.NetworkListeners.MealDetailsNetworkListener;
import com.google.android.material.datepicker.CalendarConstraints;

import java.util.Calendar;
import java.util.List;

public class MealPresenter implements MealDetailsNetworkListener {
    private MealView view;
    private Repository repository;
    UserSession userSession;
    Long today;

    public MealPresenter(MealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
        userSession = UserSession.getInstance();
        today = getCurrentDay();
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

    public long getCurrentDay()
    {
        return Calendar.getInstance().getTimeInMillis();
    }
    public CalendarConstraints.Builder getCalendarConstraints(){
        CalendarConstraints.DateValidator dateValidator = new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                return date >= today;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(android.os.Parcel dest, int flags) {
                dest.writeLong(today);
            }
        };
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(dateValidator);
        return constraintsBuilder;
    }

    public void saveMealToPlan(Meal meal, String date) {
        LocalMeal localMeal = turnRemoterMealToLocalMeal(meal);
        MealDate mealDate = new MealDate();
        mealDate.setMealId(meal.getIdMeal());
        mealDate.setUserId(userSession.getUid());
        mealDate.setDate(date);
        repository.insertMealDate(mealDate,localMeal);
    }

    public void saveMealToFavourites(Meal meal) {
        LocalMeal localMeal = turnRemoterMealToLocalMeal(meal);
        FavouriteMeal favouriteMeal = new FavouriteMeal();
        favouriteMeal.setMealId(meal.getIdMeal());
        favouriteMeal.setUserId(userSession.getUid());
        repository.insertFavoriteMeal(favouriteMeal,localMeal);

    }

    public LocalMeal turnRemoterMealToLocalMeal(Meal meal) {
        List<IngredientOfMeal> ingredients = meal.getIngredients();
        String ingredientsString = "";
        for (IngredientOfMeal ingredient : ingredients) {
            ingredientsString += ingredient.getIngredient() + "\n ";
        }
        LocalMeal localMeal = new LocalMeal();
        localMeal.setMealId(meal.getIdMeal());
        localMeal.setName(meal.getName());
        localMeal.setCategory(meal.getCategory());
        localMeal.setArea(meal.getArea());
        localMeal.setInstructions(meal.getInstructions());
        localMeal.setThumbnail(meal.getThumbnail());
        localMeal.setYoutubeLink(meal.getYoutubeLink());
        localMeal.setIngredients(ingredientsString);
        return localMeal;

    }
//    public void removeMealFromFavourites(Meal meal) {
//        FavouriteMeal favouriteMeal = new FavouriteMeal();
//        favouriteMeal.setMealId(meal.getIdMeal());
//        favouriteMeal.setUserId(userSession.getUid());
//        repository.deleteFavorite(favouriteMeal);
//    }
}
