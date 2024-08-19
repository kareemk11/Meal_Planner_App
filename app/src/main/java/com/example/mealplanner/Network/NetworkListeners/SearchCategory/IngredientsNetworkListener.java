package com.example.mealplanner.Network.NetworkListeners.SearchCategory;

import com.example.mealplanner.Model.Ingredient.Ingredient;

import java.util.List;

public interface IngredientsNetworkListener {
    void onIngredientsSuccess(List<Ingredient> ingredients);
    void onIngredientsFailure(String message);
}
