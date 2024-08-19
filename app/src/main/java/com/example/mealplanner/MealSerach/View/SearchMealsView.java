package com.example.mealplanner.MealSerach.View;

import com.example.mealplanner.Model.Area.Area;
import com.example.mealplanner.Model.Category.Category;
import com.example.mealplanner.Model.Ingredient.Ingredient;

import java.util.List;

public interface SearchMealsView {
    void showCategories(List<Category> categories);

    void showIngredients(List<Ingredient> ingredients);

    void showError(String message);

    void showAreas(List<Area> areas);

    void navigateToListedMeals(String type , String name);


}
