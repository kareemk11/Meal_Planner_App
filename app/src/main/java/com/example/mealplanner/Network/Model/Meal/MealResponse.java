package com.example.mealplanner.Network.Model.Meal;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MealResponse {

    @SerializedName("meals")
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "MealResponse{" +
                "meals=" + meals +
                '}';
    }
}
