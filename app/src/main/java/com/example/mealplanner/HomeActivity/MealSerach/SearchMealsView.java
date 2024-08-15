package com.example.mealplanner.HomeActivity.MealSerach;

import com.example.mealplanner.Model.Area.Area;
import com.example.mealplanner.Model.Category.Category;

import java.util.List;

public interface SearchMealsView {
    void showCategories(List<Category> categories);

    void showError(String message);

    void showAreas(List<Area> areas);

    void finish();
}
