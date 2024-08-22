package com.example.mealplanner.SavedMeals.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.LocalMeal.View.LocalMealActivity;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.example.mealplanner.SavedMeals.Presenter.SavedMealsPresenter;

import java.util.ArrayList;
import java.util.List;


public class SavedMealsFragment extends Fragment implements SavedMealsView, FavMealsEventsListener {

    private static final String TAG ="SavedMealsFragment" ;
    RecyclerView mealsRecyclerView;
    SavedMealAdapter adapter;
    SavedMealsPresenter presenter;
    LinearLayoutManager layoutManager;
    List<LocalMeal> meals;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        meals = new ArrayList<>();
        mealsRecyclerView = view.findViewById(R.id.favouriteMealsRecyclerView);
        mealsRecyclerView.setHasFixedSize(true);
        adapter = new SavedMealAdapter(meals, getActivity(), this);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mealsRecyclerView.setLayoutManager(layoutManager);
        mealsRecyclerView.setAdapter(adapter);

        presenter = new SavedMealsPresenter(this, Repository.getInstance(MealsRemoteDataScource.getInstance(),
                MealsLocalDataSource.getInstance(getContext())));
        presenter.getSavedMeals(this);

    }

    public SavedMealsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_meals, container, false);
    }


    @Override
    public void displayMeals(List<LocalMeal> meals) {
        Log.i(TAG, "displayMeals: "+meals);
        adapter.setSavedMealList(meals);
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void navigateToMealDetails(LocalMeal meal) {
        Intent intent = new Intent(getActivity(), LocalMealActivity.class);
        intent.putExtra("meal", meal);
        intent.putExtra("isFavourite", true);
        startActivity(intent);
    }

    @Override
    public void onCardClick(LocalMeal meal) {
        presenter.onMealCardClicked(meal);

    }

    @Override
    public void onRemoveButtonClick(LocalMeal meal) {
        presenter.deleteMeal(meal);


    }
}