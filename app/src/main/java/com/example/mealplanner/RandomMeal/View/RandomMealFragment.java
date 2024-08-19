package com.example.mealplanner.RandomMeal.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.RandomMeal.Presenter.RandomMealFragmentPresenter;
import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.Model.Meal.MealResponse;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RandomMealFragment extends Fragment implements RandomMealView {

    private static final String TAG = "RandomMealFragment";
    TextView emailView;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ImageView mealThumbnail;
    TextView mealName;
    TextView mealCategory;
    TextView mealArea;
    TextView mealInstructions;
    RandomMealFragmentPresenter presenter;
    FloatingActionButton logoutBtn;
    Button getAnotherMealBtn;

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
        super.onViewCreated(view, savedInstanceState);

        mealThumbnail = view.findViewById(R.id.mealThumbnail);
        mealName = view.findViewById(R.id.mealName);
        mealCategory = view.findViewById(R.id.mealCategory);
        mealArea = view.findViewById(R.id.mealArea);

        presenter = new RandomMealFragmentPresenter(this, Repository.getInstance(MealsRemoteDataScource.getInstance()));
        presenter.getRandomMeal();


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //logoutBtn.setOnClickListener(view1 -> presenter.Logout());

    }


    public void showRandomMeal(MealResponse mealResponse) {
        Meal meal = mealResponse.getMeals().get(0);
        mealName.setText(meal.getName());
        mealCategory.setText(meal.getCategory());
        mealArea.setText(meal.getArea());


        Glide.with(this)
                .load(meal.getThumbnail())
                .placeholder(R.drawable.ic_launcher_background) // Optional placeholder while loading
                .error(R.drawable.google) // Optional error image if loading fails
                .into(mealThumbnail);
    }

    @Override
    public void showError(String errorMessage) {
        Log.i(TAG, "showError: ");
    }

}