package com.example.mealplanner.Model.Area;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AreaResponse {
    @SerializedName("meals")
    private List<Area> meals;

    public List<Area> getAreas() {
        return meals;
    }

    public void setMeals(List<Area> meals) {
        this.meals = meals;
    }
}