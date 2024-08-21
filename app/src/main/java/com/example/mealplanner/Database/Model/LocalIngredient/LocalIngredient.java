package com.example.mealplanner.Database.Model.LocalIngredient;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;

@Entity(tableName = "ingredient",
        foreignKeys = @ForeignKey(entity = LocalMeal.class,
                parentColumns = "mealId",
                childColumns = "mealId",
                onDelete = ForeignKey.CASCADE))
public class LocalIngredient {
    @PrimaryKey(autoGenerate = true)
    public int ingredientId;
    public String mealId;
    public String ingredientName;
    public String quantity;


    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIngredient() {
        return  ingredientName+ ": " + quantity;
    }
}

