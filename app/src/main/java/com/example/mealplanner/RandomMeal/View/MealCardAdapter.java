package com.example.mealplanner.RandomMeal.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.R;
import com.google.android.material.chip.Chip;

import java.util.List;

public class MealCardAdapter extends RecyclerView.Adapter<MealCardAdapter.MealViewHolder> {

    private List<Meal> meals;
    private Context context;
    private CardClickListener cardClickListener;

    public MealCardAdapter(List<Meal> meals, Context context , CardClickListener cardClickListener) {
        this.meals = meals;
        this.context = context;
        this.cardClickListener = cardClickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardClickListener.onCardClick(meal);

            }
        });
        holder.mealTitleTextView.setText(meal.getName());
        holder.mealCategoryTextView.setText(meal.getCategory());

        Glide.with(context)
                .load(meal.getThumbnail())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mealImageView);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        CardView mealCardView;
        ImageView mealImageView;
        TextView mealTitleTextView;
        Chip mealAreaTextView;
        Chip mealCategoryTextView;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.mealThumbnail);
            mealTitleTextView = itemView.findViewById(R.id.mealName);
           // mealAreaTextView = itemView.findViewById(R.id.mealCategory);
            mealCategoryTextView = itemView.findViewById(R.id.mealCategory);
            mealCardView = itemView.findViewById(R.id.mealCard);
        }
    }
    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }
}

