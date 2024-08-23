package com.example.mealplanner.RandomMeal.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Meal.View.MealActivity;
import com.example.mealplanner.RandomMeal.Presenter.RandomMealFragmentPresenter;
import com.example.mealplanner.Network.Model.Meal.Meal;
import com.example.mealplanner.Network.Model.Meal.MealResponse;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RandomMealFragment extends Fragment implements RandomMealView {

    private static final String TAG = "RandomMealFragment";
    CardView mealCard;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ImageView mealThumbnail;
    TextView mealName;
    TextView mealCategory;
    TextView mealArea;
    RandomMealFragmentPresenter presenter;
    Meal meal;

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
        mealCard = view.findViewById(R.id.mealCard);
        presenter = new RandomMealFragmentPresenter(this, Repository.getInstance(MealsRemoteDataScource.getInstance(), MealsLocalDataSource.getInstance(getActivity())));
        presenter.getRandomMeal();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mealCard.setOnClickListener(view1 -> {
            presenter.onCardClick(meal);
        });

        //logoutBtn.setOnClickListener(view1 -> presenter.Logout());

    }


    public void showRandomMeal(MealResponse mealResponse) {
        meal = mealResponse.getMeals().get(0);
        mealName.setText(meal.getName());
        mealCategory.setText(meal.getCategory());
        mealArea.setText(meal.getArea());


        Glide.with(this)
                .load(meal.getThumbnail())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(mealThumbnail);
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

}