package com.example.mealplanner.Network;
import com.example.mealplanner.Network.Model.Area.AreaResponse;
import com.example.mealplanner.Network.Model.Category.CategoryResponse;
import com.example.mealplanner.Network.Model.Ingredient.IngredientResponse;
import com.example.mealplanner.Model.Meal.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {


    @GET("random.php")
    Call<MealResponse> getRandomMeal();


    @GET("search.php")
    Call<MealResponse> getMealsByName(@Query("s") String mealName);


    @GET("search.php")
    Call<MealResponse> getMealsByFirstLetter(@Query("f") String firstLetter);


    @GET("categories.php")
    Call<CategoryResponse> getCategories();


    @GET("filter.php")
    Call<MealResponse> getMealsByMainIngredient(@Query("i") String ingredient);


    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);


    @GET("filter.php")
    Call<MealResponse> getMealsByArea(@Query("a") String area);


    @GET("lookup.php")
    Call<MealResponse> getMealDetails(@Query("i") String mealId);


    @GET("categories.php")
    Call<CategoryResponse> getCategoriesList();


    @GET("list.php?a=list")
    Call<AreaResponse> getAreasList();


    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredientsList();
}
