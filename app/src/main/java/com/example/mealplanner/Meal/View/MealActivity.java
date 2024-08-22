package com.example.mealplanner.Meal.View;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Meal.Presenter.MealPresenter;
import com.example.mealplanner.Network.Model.Meal.IngredientOfMeal;
import com.example.mealplanner.Network.Model.Meal.Meal;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MealActivity extends AppCompatActivity implements MealView {

    private static final String TAG = "MealActivityLog";
    Meal meal;
    TextView mealTitle;
    TextView mealCategory;
    TextView mealArea;
    TextView mealInstructions;
    WebView youTubePlayer;
    ImageView mealImage;
    MaterialButton saveBtn;
    String id;
    MealPresenter presenter;
    MaterialDatePicker datePicker;
    MaterialButton addToPlanButton;
    Long today;
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
        presenter = new MealPresenter(this,
                Repository.getInstance(MealsRemoteDataScource.getInstance(), MealsLocalDataSource.getInstance(this)));
        youTubePlayer = findViewById(R.id.youTubePlayer);
        mealTitle = findViewById(R.id.mealTitle);
        mealCategory = findViewById(R.id.mealCategory);
        mealArea = findViewById(R.id.mealArea);
        mealInstructions = findViewById(R.id.mealInstructions);
        mealImage = findViewById(R.id.mealImage);
        saveBtn = findViewById(R.id.saveBtn);
        addToPlanButton = findViewById(R.id.addToPlanButton);
        today = presenter.getCurrentDay();
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date for your meal")
                .setSelection(today)
                .setCalendarConstraints(presenter.getCalendarConstraints().build())
                .setTheme(R.style.CustomDatePickerTheme)
                .build();


        WebSettings webSettings = youTubePlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        youTubePlayer.setWebViewClient(new WebViewClient());

        presenter.fetchMealDetails(id);

        saveBtn.setOnClickListener(view -> {
            presenter.saveMealToFavourites(meal);
            Toast.makeText(this, "Meal added to favourites", Toast.LENGTH_SHORT).show();
            saveBtn.setEnabled(false);
            saveBtn.animate()
                    .setDuration(300)
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .rotation(180f)
                    .withEndAction(() -> {
                        saveBtn.setScaleX(1f);
                        saveBtn.setScaleY(1f);
                        saveBtn.setRotation(0f);
                        saveBtn.setIconResource(R.drawable.baseline_download_done_24);
                    })
                    .start();


        });
        addToPlanButton.setOnClickListener(view -> datePicker.show(getSupportFragmentManager(), "DATE_PICKER"));
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Date selectedDate = new Date(selection);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = dateFormat.format(selectedDate);
            Log.i(TAG, "onCreate: " + dateString);
            presenter.saveMealToPlan(meal, dateString);
            addToPlanButton.setEnabled(false);
            addToPlanButton.setText("Meal Added");
            addToPlanButton.animate().setDuration(300).scaleX(1.2f).scaleY(1.2f).rotation(360f).withEndAction(() -> {
                saveBtn.setScaleX(1f);
                saveBtn.setScaleY(1f);
                saveBtn.setRotation(0f);
            }).start();
            Toast.makeText(this, "Meal added to plan for " + dateString, Toast.LENGTH_SHORT).show();
        });

    }


    @Override
    public void displayMealDetails(Meal meal) {
        this.meal = meal;
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