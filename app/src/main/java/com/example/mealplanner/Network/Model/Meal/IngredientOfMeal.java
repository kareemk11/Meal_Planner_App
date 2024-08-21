package com.example.mealplanner.Network.Model.Meal;

public class IngredientOfMeal {

    private String name;
    private String measurement;

    public IngredientOfMeal(String name, String measurement) {
        this.name = name;
        this.measurement = measurement;
    }

    public String getName() {
        return name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public String getIngredient() {
        return name + ": " + measurement;
    }

    @Override
    public String toString() {
        return name + ": " + measurement;
    }
}
