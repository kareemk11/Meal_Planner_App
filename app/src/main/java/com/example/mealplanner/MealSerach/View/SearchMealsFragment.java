package com.example.mealplanner.MealSerach.View;

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
import android.widget.AdapterView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.example.mealplanner.ListedMeals.View.ListedMealsActivity;
import com.example.mealplanner.MealSerach.Presenter.SearchMealsPresenter;
import com.example.mealplanner.Model.Area.Area;
import com.example.mealplanner.Model.Category.Category;
import com.example.mealplanner.Model.Ingredient.Ingredient;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;


public class SearchMealsFragment extends Fragment implements SearchMealsView, CategoryAdapterInterface {


    RecyclerView categoryRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CategoryAdapter categoryAdapter;
    List<Category> categories;
    List<Area> areas;
    private SmartMaterialSpinner<String> smartSpinnerArea;
    private SmartMaterialSpinner<String> smartSpinnerIngredient;
    private List<String> areaList;
    private List<String> ingredientList;
    SearchMealsPresenter presenter;


    private final String TAG = "SearchMealsFragment";
    private SignInClient mAuth;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categories = new ArrayList<>();
        areas = new ArrayList<>();

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryAdapter(categories, getActivity(),this);
        categoryRecyclerView.setAdapter(categoryAdapter);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
         presenter =
                new SearchMealsPresenter(Repository.getInstance(MealsRemoteDataScource.getInstance()), this);
        presenter.getCategories();
        presenter.getAreas();
        presenter.getIngredients();
        initSpinner(view);
    }

    private void initSpinner(View view) {
        smartSpinnerArea = view.findViewById(R.id.areaSearch);
        smartSpinnerIngredient = view.findViewById(R.id.ingredientSearch);
        areaList = new ArrayList<>();
        ingredientList = new ArrayList<>();
        smartSpinnerArea.setItem(areaList);
        smartSpinnerIngredient.setItem(ingredientList);
        smartSpinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                presenter.itemGotSelected( "area",  areaList.get(position));
                smartSpinnerArea.clearSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        smartSpinnerIngredient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                presenter.itemGotSelected("ingredient", ingredientList.get(i));
                smartSpinnerIngredient.clearSelection();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void showCategories(List<Category> categories) {
        categoryAdapter.setCategoryList(categories);
        Log.i(TAG, "showCategories: " + categories);
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        for(int i = 0; i < ingredients.size(); i++)
        {
            ingredientList.add(ingredients.get(i).getStrIngredient());

        }
        smartSpinnerIngredient.setItem(ingredientList);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void showAreas(List<Area> areas) {
        for (int i = 0; i < areas.size(); i++) {
            areaList.add(areas.get(i).getStrArea());
        }
        smartSpinnerArea.setItem(areaList);
    }


    @Override
    public void navigateToListedMeals(String type , String name) {
        Intent intent = new Intent(getActivity(), ListedMealsActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    public SearchMealsFragment() {
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
        return inflater.inflate(R.layout.fragment_search_meals, container, false);
    }

    @Override
    public void onCategoryClick(String categoryName) {
        presenter.itemGotSelected("category", categoryName);
    }
}