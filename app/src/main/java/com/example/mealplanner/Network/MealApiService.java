package com.example.mealplanner.Network;
import com.example.mealplanner.Model.AreaResponse;
import com.example.mealplanner.Model.CategoryResponse;
import com.example.mealplanner.Model.IngredientResponse;
import com.example.mealplanner.Model.Meal;
import com.example.mealplanner.Model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    // Get a random meal
    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    // Search for meals by name
    @GET("search.php")
    Call<MealResponse> searchMeals(@Query("s") String mealName);

    // List all meals by the first letter
    @GET("search.php")
    Call<MealResponse> getMealsByFirstLetter(@Query("f") String firstLetter);

    // List all meal categories
    @GET("categories.php")
    Call<CategoryResponse> getCategories();

    // Filter meals by main ingredient
    @GET("filter.php")
    Call<MealResponse> getMealsByMainIngredient(@Query("i") String ingredient);

    // Filter meals by category
    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    // Filter meals by area (country)
    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);

    // Get meal details by ID
    @GET("lookup.php")
    Call<MealResponse> getMealDetails(@Query("i") String mealId);

    // List all categories
    @GET("list.php?c=list")
    Call<CategoryResponse> getCategoriesList();

    // List all areas
    @GET("list.php?a=list")
    Call<AreaResponse> getAreasList();

    // List all ingredients
    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredientsList();
}
