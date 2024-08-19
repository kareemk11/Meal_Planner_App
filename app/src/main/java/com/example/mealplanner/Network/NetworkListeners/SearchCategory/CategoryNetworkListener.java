package com.example.mealplanner.Network.NetworkListeners.SearchCategory;

import com.example.mealplanner.Model.Category.Category;

import java.util.List;

public interface CategoryNetworkListener {

    void onMealsCategorySuccess(List<Category> meals);

    void onMealsCategoryFailure(String message);
}
