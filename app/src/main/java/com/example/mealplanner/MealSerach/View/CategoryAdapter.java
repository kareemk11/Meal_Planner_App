package com.example.mealplanner.MealSerach.View;

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
import com.example.mealplanner.Model.Category.Category;
import com.example.mealplanner.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private Context context;
    private CategoryAdapterInterface categoryAdapterInterface;

    public CategoryAdapter(List<Category> categoryList, Context context, CategoryAdapterInterface categoryAdapterInterface) {
        this.categoryList = categoryList;
        this.context = context;
        this.categoryAdapterInterface = categoryAdapterInterface;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryrow, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImageView;
        private TextView categoryNameTextView;
        private CardView categoryCard;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageView = itemView.findViewById(R.id.categoryThumbnail);
            categoryNameTextView = itemView.findViewById(R.id.categoryName);
            categoryCard = itemView.findViewById(R.id.categoryCard);
        }

        public void bind(Category category) {
            categoryNameTextView.setText(category.getStrCategory());
            categoryCard.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    categoryAdapterInterface.onCategoryClick(category.getStrCategory());
                }
            });
            Glide.with(context)
                    .load(category.getStrCategoryThumb())
                    .into(categoryImageView);
        }
    }
}

