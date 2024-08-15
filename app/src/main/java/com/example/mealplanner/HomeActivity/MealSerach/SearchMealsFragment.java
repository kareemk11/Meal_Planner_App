package com.example.mealplanner.HomeActivity.MealSerach;

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
import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.Model.Area.Area;
import com.example.mealplanner.Model.Category.Category;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;


public class SearchMealsFragment extends Fragment implements SearchMealsView {

    FloatingActionButton logoutBtn;
    RecyclerView categoryRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CategoryAdapter categoryAdapter;
    List<Category> categories;
    List<Area> areas;
    private SmartMaterialSpinner<String> spProvince;
    private List<String> provinceList;
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
        categoryAdapter = new CategoryAdapter(categories, getContext());
        categoryRecyclerView.setAdapter(categoryAdapter);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
         presenter =
                new SearchMealsPresenter(Repository.getInstance(MealsRemoteDataScource.getInstance()), this);
        presenter.getCategories();
        presenter.getAreas();
        presenter.getAreas();
        initSpinner(view);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(view1 -> presenter.Logout());

    }

    private void initSpinner(View view) {
        spProvince = view.findViewById(R.id.sp_provinces);
        provinceList = new ArrayList<>();
        spProvince.setItem(provinceList);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getContext(), provinceList.get(position), Toast.LENGTH_SHORT).show();
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
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void showAreas(List<Area> areas) {
        for (int i = 0; i < areas.size(); i++) {
            provinceList.add(areas.get(i).getStrArea());
        }
        spProvince.setItem(provinceList);
    }

    @Override
    public void finish() {

        mAuth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
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

}