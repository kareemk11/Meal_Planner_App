package com.example.mealplanner.MealsPlan.View;

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
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.LocalMeal.View.LocalMealActivity;
import com.example.mealplanner.MealsPlan.Presenter.MealsPlanPresenter;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MealsPlanFragment extends Fragment implements MealsPlanView, MealsByDateEventListener {
    RecyclerView mealsPlanRecyclerView;
    MealsPlanAdapter mealsPlanAdapter;
    MealsPlanPresenter mealsPlanPresenter;
    LinearLayoutManager layoutManager;
    List<LocalMeal> meals;
    TextView guestMessage;
    TextView guestBtn;
    SearchView searchMealPlan;
    private static final String TAG = "MealsPlanFragmentLog";
    private boolean isGuest;
    public Comparator<LocalMeal> sortByData;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        isGuest = UserSession.getInstance().getGuest();
        mealsPlanPresenter = new MealsPlanPresenter(this, Repository.getInstance(MealsRemoteDataScource.getInstance(),
                MealsLocalDataSource.getInstance(getContext())));
        super.onViewCreated(view, savedInstanceState);
        if (isGuest) {
            view.findViewById(R.id.mealPlanGroup).setVisibility(View.VISIBLE);
            view.findViewById(R.id.searchMealPlan).setVisibility(View.GONE);
            guestMessage = view.findViewById(R.id.guest_message);
            guestBtn = view.findViewById(R.id.guest_btn);
            guestBtn.setOnClickListener(view1 -> {
                mealsPlanPresenter.onGuestBtnClicked();
            });

        }
        else
        {
            view.findViewById(R.id.mealPlanGroup).setVisibility(View.GONE);
            searchMealPlan = view.findViewById(R.id.searchMealPlan);
            mealsPlanRecyclerView = view.findViewById(R.id.mealsByDateRecyclerView);
            recyclerViewInit();
            mealsPlanPresenter.getMealsPlan(this);
            searchMealPlan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mealsPlanAdapter.filter(query);
                    mealsPlanAdapter.sortItems(sortByData);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i(TAG, "onQueryTextChange: "+newText);
                    mealsPlanAdapter.filter(newText);
                    mealsPlanAdapter.sortItems(sortByData);
                    return false;
                }
            });
            sortByData = new Comparator<LocalMeal>() {
                @Override
                public int compare(LocalMeal o1, LocalMeal o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            };
            mealsPlanAdapter.sortItems(sortByData);

        }






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
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        mealsPlanAdapter.sortItems(sortByData);
    }

    @Override
    public void showError(String errorMessage) {

    }


    @Override
    public void onCardClick(LocalMeal meal) {
        mealsPlanPresenter.onMealCardClicked(meal);

    }

    @Override
    public void onRemoveButtonClick(LocalMeal meal) {

        mealsPlanPresenter.deleteMeal(meal);
    }
    @Override
    public void navigateToMealDetails(LocalMeal meal) {
        Intent intent = new Intent(getActivity(), LocalMealActivity.class);
        intent.putExtra("meal", meal);
        intent.putExtra("isFavourite", false);
        startActivity(intent);
    }

    @Override
    public void navigateToLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}