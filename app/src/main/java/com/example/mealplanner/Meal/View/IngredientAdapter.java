package com.example.mealplanner.Meal.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.Model.Meal.IngredientOfMeal;
import com.example.mealplanner.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<IngredientOfMeal> ingredients;

    public IngredientAdapter(List<IngredientOfMeal> ingredients) {
        this.ingredients = ingredients;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ingredientText);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(ingredients.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
    public void updateIngredients(List<IngredientOfMeal> newIngredients) {
        ingredients = newIngredients;
        notifyDataSetChanged();
    }
}
