package com.example.mealplanner.ListedMeals.View;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.ListedMeals.Presenter.SearchedMealsPresenter;
import com.example.mealplanner.Meal.MealActivity;
import com.example.mealplanner.Network.Model.Meal.Meal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;

import java.util.ArrayList;
import java.util.List;

public class ListedMealsActivity extends AppCompatActivity implements ListedMealsEventsListener,SearchedMealsView {

    String type;
    String name;
    RecyclerView mealsRecyclerView;
    SearchedMealAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<Meal> meals;
    SearchedMealsPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        presenter = new SearchedMealsPresenter(Repository.getInstance(MealsRemoteDataScource.getInstance(), MealsLocalDataSource.getInstance(this)),this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listed_meals);
        mealsRecyclerView = findViewById(R.id.favouriteMealsRecyclerView);
        recycleViewInit();
        if (type.equals("category")) {
            presenter.searchMealsByCategory(name);
        }
        else if (type.equals("area")) {
            presenter.searchMealsByArea(name);
        }
        else if (type.equals("ingredient")) {

            presenter.searchMealsByIngredient(name);
        }
    }

    private void recycleViewInit() {

        mealsRecyclerView.setHasFixedSize(true);
        meals = new ArrayList<>();
        adapter = new SearchedMealAdapter(meals, this, this);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mealsRecyclerView.setLayoutManager(linearLayoutManager);
        mealsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSavelClick(Meal meal) {


    }

    @Override
    public void onCardClick(Meal meal) {

        presenter.onCardClick(meal);
    }

    @Override
    public void showMeals(List<Meal> meals) {
        adapter.setMealList(meals);

    }
    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMealActivity(Meal meal) {
        Intent intent = new Intent(this, MealActivity.class);
        intent.putExtra("id", meal.getIdMeal());
        startActivity(intent);
    }
}