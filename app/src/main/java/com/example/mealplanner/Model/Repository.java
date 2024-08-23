package com.example.mealplanner.Model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Database.Model.MealDate.MealDate;
import com.example.mealplanner.Database.Model.User.User;
import com.example.mealplanner.Meal.Presenter.MealPresenter;
import com.example.mealplanner.Meal.View.IfMealAddedToPlan;
import com.example.mealplanner.Meal.View.IfMealIsFavouriteListener;
import com.example.mealplanner.Network.NetworkListeners.MealDetailsNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByAreaNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.AreaNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.IngredientsNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.SearchCategory.CategoryNetworkListener;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByCategoryNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.ListedMeals.MealsByMainIngredientNetworkListener;
import com.example.mealplanner.Network.NetworkListeners.RandomMealNetworkListener;
import com.example.mealplanner.RandomMeal.Presenter.RandomMealFragmentPresenter;

import java.util.List;

public class Repository {
    private static final String TAG = "RepositoryLog";
    private static Repository instance;
    private MealsRemoteDataScource mealsRemoteDataScource;
    private MealsLocalDataSource mealsLocalDataSource;


    private Repository(MealsRemoteDataScource mealsRemoteDataScource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataScource = mealsRemoteDataScource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }

    public static Repository getInstance(MealsRemoteDataScource mealsRemoteDataScource, MealsLocalDataSource mealsLocalDataSource) {
        if (instance == null) {
            instance = new Repository(mealsRemoteDataScource, mealsLocalDataSource);
        }
        return instance;
    }
    public void getRandomMeal(RandomMealNetworkListener listener) {
        mealsRemoteDataScource.getRandomMeal(listener);
    }
    public void getCategories(CategoryNetworkListener listener) {
        mealsRemoteDataScource.getCategories(listener);
    }
    public void getAreas(AreaNetworkListener listener) {
        mealsRemoteDataScource.getAreas(listener);
    }
    public void getIngredients(IngredientsNetworkListener listener) {
        mealsRemoteDataScource.getIngredients(listener);
    }
    public void getMealsByCategory(String category, MealsByCategoryNetworkListener listener) {
        mealsRemoteDataScource.getMealsByCategory(category,listener);
    }
    public void getMealsByArea(String area, MealsByAreaNetworkListener listener) {
        mealsRemoteDataScource.getMealsByArea(area,listener);
    }
    public void getMealsByIngredient(String ingredient, MealsByMainIngredientNetworkListener listener) {
        mealsRemoteDataScource.getMealsByMainIngredient(ingredient,listener);
    }
    public void getMealDetails(String mealId, MealDetailsNetworkListener listener) {
        mealsRemoteDataScource.getMealDetails(mealId,listener);
    }
    public void insertUser(User user) {
         mealsLocalDataSource.insertUser(user);
    }
    public void insertMeal(LocalMeal meal) {
        mealsLocalDataSource.insertMeal(meal);
    }
    public void deleteMeal(LocalMeal meal) {
        mealsLocalDataSource.deleteMeal(meal);
    }
    public void insertFavoriteMeal(FavouriteMeal favorite,LocalMeal meal) {
        mealsLocalDataSource.insertFavorite(favorite,meal);
    }
    public void deleteFavorite(LocalMeal favorite) {
        mealsLocalDataSource.deleteFavorite(favorite);
    }

    public LiveData<List<FavouriteMeal>> getSavedMeals(String userId) {
        return mealsLocalDataSource.getFavoritesByUserId(userId);
    }
    public LiveData<LocalMeal> getMealById(String mealId) {
        return mealsLocalDataSource.getMealById(mealId);
    }

    public void insertMealDate(MealDate mealDate, LocalMeal localMeal) {
        mealsLocalDataSource.insertMealDate(mealDate, localMeal);
    }
    public void deleteMealDate(LocalMeal mealDate) {
        mealsLocalDataSource.deleteMealDate(mealDate);
    }
    public LiveData<List<MealDate>> getDatedMealsByUserId(String userId) {
        return mealsLocalDataSource.getDatedMealsByUserId(userId);
    }

    public void isMealFavourite(String mealId, String userId, IfMealIsFavouriteListener listener) {
        Log.i(TAG, "isMealFavourite: ");
        mealsLocalDataSource.getFavoriteByMealIdAndUserId(mealId, userId, listener);
    }

    public void isMealAddedToPlan(String id, String uid, IfMealAddedToPlan listener) {
        mealsLocalDataSource.getDateMealByMealIdAndUserId(id, uid, listener);
    }

    public void getMultiplesMeals(RandomMealNetworkListener listener) {

        mealsRemoteDataScource.getMultipleRandomMeals(listener);

    }
}
