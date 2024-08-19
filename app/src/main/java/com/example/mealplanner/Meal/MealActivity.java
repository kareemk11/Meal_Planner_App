package com.example.mealplanner.Meal;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.Model.Meal.IngredientOfMeal;
import com.example.mealplanner.Model.Meal.Meal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealActivity extends AppCompatActivity implements MealView {

    TextView mealTitle;
    TextView mealCategory;
    TextView mealArea;
    TextView mealInstructions;
    WebView youTubePlayer;
    ImageView mealImage;
    String id;
    MealPresenter presenter;
    private RecyclerView ingredientsList;
    private IngredientAdapter ingredientsAdapter;
    private List<IngredientOfMeal> ingredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        ingredients = new ArrayList<>();
        ingredientsList = findViewById(R.id.ingredientsList);
        ingredientsAdapter = new IngredientAdapter(ingredients);
        ingredientsList.setNestedScrollingEnabled(false);
        ingredientsList.setLayoutManager(new LinearLayoutManager(this));
        ingredientsList.setAdapter(ingredientsAdapter);
        id = getIntent().getStringExtra("id");
        presenter = new MealPresenter(this, Repository.getInstance(MealsRemoteDataScource.getInstance()));
        youTubePlayer = findViewById(R.id.youTubePlayer);
        mealTitle = findViewById(R.id.mealTitle);
        mealCategory = findViewById(R.id.mealCategory);
        mealArea = findViewById(R.id.mealArea);
        mealInstructions = findViewById(R.id.mealInstructions);
        mealImage = findViewById(R.id.mealImage);

        WebSettings webSettings = youTubePlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        youTubePlayer.setWebViewClient(new WebViewClient());

        presenter.fetchMealDetails(id);

    }


    @Override
    public void displayMealDetails(Meal meal) {
        mealTitle.setText(meal.getName());
        mealCategory.setText(meal.getCategory());
        mealArea.setText(meal.getArea());
        mealInstructions.setText(meal.getInstructions());
        ingredientsAdapter.updateIngredients(meal.getIngredients());
        Glide.with(this).load(meal.getThumbnail()).into(mealImage);
        String videoUrl = meal.getYoutubeLink();
        String videoId = extractYouTubeId(videoUrl);
        String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=0";
        youTubePlayer.loadUrl(embedUrl);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

    }

    public String extractYouTubeId(String url) {
        String videoId = null;
        String pattern = "^(http(s)?:\\/\\/)?(www\\.)?(youtube\\.com|youtu\\.?be)\\/(watch\\?v=|embed\\/|v\\/|.+\\?v=)?([^&=%\\?]{11})";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            videoId = matcher.group(6);
        }

        return videoId;
    }


}