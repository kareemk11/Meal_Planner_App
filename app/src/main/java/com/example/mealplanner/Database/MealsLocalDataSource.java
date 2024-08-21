package com.example.mealplanner.Database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.Database.Model.Favourite.FavouriteDao;
import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.LocalIngredient.IngredientDao;
import com.example.mealplanner.Database.Model.LocalIngredient.LocalIngredient;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMealDao;
import com.example.mealplanner.Database.Model.MealDate.MealDate;
import com.example.mealplanner.Database.Model.MealDate.MealDateDao;
import com.example.mealplanner.Database.Model.User.User;
import com.example.mealplanner.Database.Model.User.UserDao;

import java.util.List;

public class MealsLocalDataSource {

    private static MealsLocalDataSource instance = null;
    private LocalMealDao localMealDao;
    private IngredientDao ingredientDao;
    private MealDateDao mealDateDao;
    private FavouriteDao favouriteDao;
    private UserDao userDao;
    private Context context;
    private LiveData<List<FavouriteMeal>> favorites;
    private LiveData<List<LocalIngredient>> ingredients;
    private LiveData<LocalMeal> meal;
    private LiveData<List<MealDate>> mealsByDate;
    private LiveData<User> user;


    private MealsLocalDataSource(Context context) {
        this.context = context;
        MealPlannerDatabase database = MealPlannerDatabase.getInstance(this.context.getApplicationContext());
        localMealDao = database.localMealDao();
        ingredientDao = database.ingredientDao();
        mealDateDao = database.mealDateDao();
        favouriteDao = database.favouriteDao();
        userDao = database.userDao();

    }

    public static MealsLocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new MealsLocalDataSource(context);
        }
        return instance;
    }


    public  void insertMeal(LocalMeal meal) {

        new Thread(() -> {
            localMealDao.insertMeal(meal);
        }).start();
    }

    public void deleteMeal(LocalMeal meal) {
        new Thread(() -> {
            localMealDao.deleteMeal(meal);
        }).start();
    }

    public LiveData<LocalMeal> getMealById(String mealId) {
        return localMealDao.getMealById(mealId);
    }

    public void insertIngredient(LocalIngredient ingredient) {
        new Thread(() -> {
            ingredientDao.insertIngredient(ingredient);
        }).start();
    }

    public LiveData<List<FavouriteMeal>> getFavoritesByUserId(String userId) {

        return favouriteDao.getFavoritesByUserId(userId);
    }

    public void insertFavorite(FavouriteMeal favorite, LocalMeal meal) {
        new Thread(() -> {
            localMealDao.insertMeal(meal);
            favouriteDao.insertFavorite(favorite);
        }).start();
    }

    public void deleteFavorite(LocalMeal meal) {
        new Thread(() -> {
            favouriteDao.deleteFavoriteByMealId(meal.getMealId());
        }).start();
    }

    public Long insertUser(User user) {
        final Long[] id = new Long[1];
        new Thread(() -> {
            id[0] = userDao.insertUser(user);
        }).start();
        return id[0];
    }

    public LiveData<List<MealDate>> getMealsByDate(String userId, String date) {
        return mealDateDao.getMealsByDate(userId, date);
    }

    public void insertMealDate(MealDate mealDate, LocalMeal meal) {
        new Thread(() -> {
            localMealDao.insertMeal(meal);
            mealDateDao.insertMealDate(mealDate);
        }).start();
    }

    public LiveData<List<MealDate>> getDatedMealsByUserId(String userId) {
        return mealDateDao.getDatedMealsByUserId(userId);
    }

    public void deleteMealDate(LocalMeal mealDate) {
        new Thread(() -> {
            mealDateDao.deleteMealDateByMealId(mealDate.getMealId());
        }).start();
    }
}
