package com.example.mealplanner.ListedMeals.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SearchedMealAdapter extends RecyclerView.Adapter<SearchedMealAdapter.MealViewHolder> {

    private List<Meal> mealList;
    private Context context;
    private ListedMealsEventsListener listener;

    public SearchedMealAdapter(List<Meal> mealList, Context context, ListedMealsEventsListener listener) {
        this.mealList = mealList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mealrow, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.mealName.setText(meal.getName());
        holder.mealCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCardClick(meal);
            }
        });
        Glide.with(context)
                .load(meal.getThumbnail())
                .into(holder.mealThumbnail);

        holder.saveButton.setOnClickListener(v -> {
           listener.onSavelClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void setMealList(List<Meal> meals) {
        mealList = meals;
        notifyDataSetChanged();
    }

    // ViewHolder as an inner class
    public class MealViewHolder extends RecyclerView.ViewHolder {

        ImageView mealThumbnail;
        TextView mealName;
        MaterialButton saveButton;
        CardView mealCard;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealThumbnail = itemView.findViewById(R.id.mealThumbnail);
            mealName = itemView.findViewById(R.id.mealName);
            saveButton = itemView.findViewById(R.id.saveButton);
            mealCard = itemView.findViewById(R.id.mealCard);
        }
    }


}

