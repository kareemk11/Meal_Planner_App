package com.example.mealplanner.FavouriteMeals.View;

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
import android.widget.TextView;

import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.LocalMeal.View.LocalMealActivity;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.example.mealplanner.FavouriteMeals.Presenter.FavouriteMealsPresenter;

import java.util.ArrayList;
import java.util.List;


public class FavouriteMealsFragment extends Fragment implements FavouriteMealsView, FavouriteMealsEventsListener {

    private static final String TAG ="SavedMealsFragment" ;
    RecyclerView mealsRecyclerView;
    FavouriteMealAdapter adapter;
    FavouriteMealsPresenter presenter;
    LinearLayoutManager layoutManager;
    List<LocalMeal> meals;
    TextView guestMessage;
    TextView guestBtn;
    private boolean isGuest;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        isGuest = UserSession.getInstance().getGuest();
        presenter = new FavouriteMealsPresenter(this, Repository.getInstance(MealsRemoteDataScource.getInstance(),
                MealsLocalDataSource.getInstance(getActivity())));
        super.onViewCreated(view, savedInstanceState);

        if (isGuest) {
            view.findViewById(R.id.mealPlanGroup).setVisibility(View.VISIBLE);
        }
        else{
            view.findViewById(R.id.mealPlanGroup).setVisibility(View.GONE);
            mealsRecyclerView = view.findViewById(R.id.favouriteMealsRecyclerView);
            recyclerViewInit();
            presenter.getSavedMeals(this);
        }

        guestMessage = view.findViewById(R.id.guest_message);
        guestBtn = view.findViewById(R.id.guest_btn);
        guestBtn.setOnClickListener(view1 -> {
            presenter.onGuestBtnClicked();
        });



    }

   private void recyclerViewInit(){

       meals = new ArrayList<>();

       mealsRecyclerView.setHasFixedSize(true);
       adapter = new FavouriteMealAdapter(meals, getActivity(), this);
       layoutManager = new LinearLayoutManager(getActivity());
       layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
       mealsRecyclerView.setLayoutManager(layoutManager);
       mealsRecyclerView.setAdapter(adapter);
    }

    public FavouriteMealsFragment() {
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
        return inflater.inflate(R.layout.fragment_favourite_meals, container, false);
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

    @Override
    public void navigateToLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}