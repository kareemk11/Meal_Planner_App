package com.example.mealplanner.MealsPlan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.example.mealplanner.SavedMeals.SavedMealAdapter;

import java.util.ArrayList;
import java.util.List;


public class MealsPlanFragment extends Fragment implements MealsPlanView, MealsByDateEventListener {
    RecyclerView mealsPlanRecyclerView;
    MealsPlanAdapter mealsPlanAdapter;
    MealsPlanPresenter mealsPlanPresenter;
    LinearLayoutManager layoutManager;
    List<LocalMeal> meals;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealsPlanRecyclerView = view.findViewById(R.id.mealsByDateRecyclerView);
        recyclerViewInit();
        mealsPlanPresenter = new MealsPlanPresenter(this, Repository.getInstance(MealsRemoteDataScource.getInstance(),
                MealsLocalDataSource.getInstance(getContext())));
        mealsPlanPresenter.getMealsPlan(this);


    }

    private void recyclerViewInit() {

        meals = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        mealsPlanAdapter = new MealsPlanAdapter(meals, getActivity(), this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mealsPlanRecyclerView.setLayoutManager(layoutManager);
        mealsPlanRecyclerView.setHasFixedSize(true);
        mealsPlanRecyclerView.setAdapter(mealsPlanAdapter);
    }

    public MealsPlanFragment() {
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
        return inflater.inflate(R.layout.fragment_meals_plan, container, false);
    }

    @Override
    public void displayMealsForDate(List<LocalMeal> meals) {
        mealsPlanAdapter.setMealsPlanList(meals);
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onMealSaved(LocalMeal meal) {

    }

    @Override
    public void onMealDeleted(LocalMeal meal) {

    }


    @Override
    public void onCardClick(LocalMeal meal) {

    }

    @Override
    public void onRemoveButtonClick(LocalMeal meal) {

        mealsPlanPresenter.deleteMeal(meal);
    }
}