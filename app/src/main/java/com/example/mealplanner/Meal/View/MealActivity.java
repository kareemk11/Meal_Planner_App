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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Meal.Presenter.MealPresenter;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.Model.Meal.IngredientOfMeal;
import com.example.mealplanner.Model.Meal.Meal;
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
    MaterialDatePicker<Long> datePicker;
    MaterialButton addToPlanButton;
    Long today;
    private RecyclerView ingredientsList;
    private IngredientAdapter ingredientsAdapter;
    private List<IngredientOfMeal> ingredients;
    private boolean isGuest;
    private boolean isFavourite;
    private boolean isAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        presenter = new MealPresenter(this,
                Repository.getInstance(MealsRemoteDataScource.getInstance(), MealsLocalDataSource.getInstance(this)));
        isGuest = UserSession.getInstance().getGuest();
        id = getIntent().getStringExtra("id");
        super.onCreate(savedInstanceState);
        today = presenter.getCurrentDay();
        setContentView(R.layout.activity_meal);
        ingredientsList = findViewById(R.id.ingredientsList);
        recyclerViewInit();
        findViewInit();
        dataPickerInit();
        if (!isGuest) {
            presenter.isMealFavourite(id);
            presenter.isMealAddedToPlan(id);
        }
        presenter.fetchMealDetails(id);
        saveBtn.setOnClickListener(view -> {
            if (isGuest) {
                Toast.makeText(this, "You must be logged in to add a meal to favourites", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isFavourite) {
                Log.i(TAG, "onCreate: " + isFavourite);
                Toast.makeText(this, "Meal already added to favourites", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.i(TAG, "onCreate: " + isFavourite);
                presenter.saveMealToFavourites(meal);
                Toast.makeText(this, "Meal added to favourites", Toast.LENGTH_SHORT).show();
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
                isFavourite = true;
        });
        addToPlanButton.setOnClickListener(view -> {
            if (isGuest) {
                Toast.makeText(this, "You must be logged in to add a meal to your plan", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isAdded) {
                Toast.makeText(this, "Meal already added to plan", Toast.LENGTH_SHORT).show();
                return;
            }
            isAdded = true;
            datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
        });


    }

    private void dataPickerInit() {

        datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date for your meal")
                .setSelection(today)
                .setCalendarConstraints(presenter.getCalendarConstraints().build())
                .setTheme(R.style.CustomDatePickerTheme)
                .build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            Date selectedDate = new Date(selection);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateString = dateFormat.format(selectedDate);
            presenter.saveMealToPlan(meal, dateString);
            addToPlanButton.setText(R.string.meal_added);
            addToPlanButton.animate().setDuration(300).scaleX(1.2f).scaleY(1.2f).rotation(360f).withEndAction(() -> {
                saveBtn.setScaleX(1f);
                saveBtn.setScaleY(1f);
                saveBtn.setRotation(0f);
            }).start();
            Toast.makeText(this, "Meal added to plan for " + dateString, Toast.LENGTH_SHORT).show();
        });
    }

    private void findViewInit() {
        youTubePlayer = findViewById(R.id.youTubePlayer);
        mealTitle = findViewById(R.id.mealTitle);
        mealCategory = findViewById(R.id.mealCategory);
        mealArea = findViewById(R.id.mealArea);
        mealInstructions = findViewById(R.id.mealInstructions);
        mealImage = findViewById(R.id.mealImage);
        saveBtn = findViewById(R.id.saveBtn);
        addToPlanButton = findViewById(R.id.addToPlanButton);
        WebSettings webSettings = youTubePlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        youTubePlayer.setWebViewClient(new WebViewClient());

    }

    private void recyclerViewInit() {

        ingredients = new ArrayList<>();
        ingredientsAdapter = new IngredientAdapter(ingredients);
        ingredientsList.setNestedScrollingEnabled(false);
        ingredientsList.setLayoutManager(new LinearLayoutManager(this));
        ingredientsList.setAdapter(ingredientsAdapter);
    }


    @Override
    public void displayMealDetails(Meal meal) {
        this.meal = meal;
        mealTitle.setText(meal.getName());
        mealCategory.setText(meal.getCategory());
        mealArea.setText(meal.getArea());
        mealInstructions.setText(meal.getInstructions());
        ingredientsAdapter.updateIngredients(meal.getIngredients());
        Glide.with(this).load(meal.getThumbnail())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(mealImage);
        String videoUrl = meal.getYoutubeLink();
        String videoId = presenter.extractYouTubeId(videoUrl);
        String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=0";
        youTubePlayer.loadUrl(embedUrl);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setMealAddedToFavourites(boolean isFavourite) {
        this.isFavourite = isFavourite;
        saveBtn.setIconResource(isFavourite ? R.drawable.baseline_download_done_24 : R.drawable.baseline_arrow_downward_24);
    }

    @Override
    public void setMealAddedToPlan(boolean isAdded) {
        this.isAdded = isAdded;
        addToPlanButton.setText(isAdded ? R.string.meal_added : R.string.add_to_plan);
    }


//    public String extractYouTubeId(String url) {
//        String videoId = null;
//        String pattern = "^(http(s)?:\\/\\/)?(www\\.)?(youtube\\.com|youtu\\.?be)\\/(watch\\?v=|embed\\/|v\\/|.+\\?v=)?([^&=%\\?]{11})";
//
//        Pattern compiledPattern = Pattern.compile(pattern);
//        Matcher matcher = compiledPattern.matcher(url);
//
//        if (matcher.find()) {
//            videoId = matcher.group(6);
//        }
//
//        return videoId;
//    }


}