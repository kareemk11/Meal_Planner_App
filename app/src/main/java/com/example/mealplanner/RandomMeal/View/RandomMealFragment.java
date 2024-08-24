package com.example.mealplanner.RandomMeal.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Meal.View.MealActivity;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.RandomMeal.Presenter.RandomMealFragmentPresenter;
import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.Model.Meal.MealResponse;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class RandomMealFragment extends Fragment implements RandomMealView , CardClickListener{

    private static final String TAG = "RandomMealFragment";
    RandomMealFragmentPresenter presenter;
    private RecyclerView recyclerView;
    private MealCardAdapter adapter;
    private List<Meal> mealList;
    TextView welcomeTextView;
    boolean isGuest;
    Button loginBtn;


    public RandomMealFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        isGuest = UserSession.getInstance().getGuest();
        super.onViewCreated(view, savedInstanceState);
        loginBtn = view.findViewById(R.id.guest_btn);
        if (isGuest) {
            loginBtn.setVisibility(View.VISIBLE);
        }
        loginBtn.setOnClickListener(view1 -> presenter.onGuestBtnClicked());
        mealList = new ArrayList<>();
        adapter = new MealCardAdapter( mealList, getActivity(), this);
        recyclerView = view.findViewById(R.id.mealRecyclerView);
        welcomeTextView = view.findViewById(R.id.welcomeTxt);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(adapter);
        presenter = new RandomMealFragmentPresenter(this,
                Repository.getInstance(MealsRemoteDataScource.getInstance(),
                        MealsLocalDataSource.getInstance(getActivity())));

        presenter.getMultiplesMeals();
        if (isGuest) {
            welcomeTextView.append(" login to enjoy offline mode");
        }
        else {
            welcomeTextView.append(" " + UserSession.getInstance().getUsername());
        }




    }



    @Override
    public void showError(String errorMessage) {
        Log.i(TAG, "showError: ");
    }


    public void navigateToMealActivity(Meal meal) {
        Intent intent = new Intent(getActivity(), MealActivity.class);
        intent.putExtra("id", meal.getIdMeal());
        startActivity(intent);
    }

    @Override
    public void showMultiplesMeals(Meal meal) {
        Log.i(TAG, "showMultiplesMeals: "+meal.getName());
        mealList.add(meal);
        adapter.setMeals(mealList);
    }

    @Override
    public void navigateToLoginScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCardClick(Meal meal) {
        presenter.onCardClick(meal);
    }
}