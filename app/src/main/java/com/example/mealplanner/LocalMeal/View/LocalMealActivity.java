package com.example.mealplanner.LocalMeal.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.LocalMeal.Presenter.LocalMealPresenter;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.android.material.button.MaterialButton;

public class LocalMealActivity extends AppCompatActivity implements LocalMealView {

    TextView mealTitle;
    TextView mealCategory;
    TextView mealArea;
    TextView mealInstructions;
    TextView ingredients;
    WebView youTubePlayer;
    ImageView mealImage;
    MaterialButton saveBtn;
    LocalMealPresenter presenter;
    MaterialButton addToPlanButton;
    LocalMeal meal;
    boolean isFavourite ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meal = (LocalMeal) getIntent().getSerializableExtra("meal");
        isFavourite = getIntent().getBooleanExtra("isFavourite", false);
        setContentView(R.layout.activity_local_meal);
        mealTitle = findViewById(R.id.mealTitle);
        mealCategory = findViewById(R.id.mealCategory);
        mealArea = findViewById(R.id.mealArea);
        mealInstructions = findViewById(R.id.mealInstructions);
        ingredients = findViewById(R.id.ingredientsTxt);
        youTubePlayer = findViewById(R.id.youTubePlayer);
        mealImage = findViewById(R.id.mealImage);
        saveBtn = findViewById(R.id.saveBtn);
        addToPlanButton = findViewById(R.id.addToPlanButton);

        presenter = new LocalMealPresenter(this, Repository.getInstance(MealsRemoteDataScource.getInstance(), MealsLocalDataSource.getInstance(this)));
        WebSettings webSettings = youTubePlayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        youTubePlayer.setWebViewClient(new WebViewClient());
        displayMealDetails();

        if (isFavourite) {
            saveBtn.setVisibility(View.VISIBLE);
            addToPlanButton.setVisibility(View.GONE);

        } else {
            addToPlanButton.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.GONE);
        }

        saveBtn.setOnClickListener(view -> showConfirmDialog("Favourites"));

        addToPlanButton.setOnClickListener(view -> showConfirmDialog("Plan"));


    }

    private void displayMealDetails() {
        mealTitle.setText(meal.getName());
        mealCategory.setText(meal.getCategory());
        mealArea.setText(meal.getArea());
        mealInstructions.setText(meal.getInstructions());
        ingredients.setText(meal.getIngredients());
        Glide.with(this).
                load(meal.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(mealImage);
        String videoUrl = meal.getYoutubeLink();
        String videoId = presenter.extractYouTubeId(videoUrl);
        String embedUrl = "https://www.youtube.com/embed/" + videoId + "?autoplay=0";
        youTubePlayer.loadUrl(embedUrl);
    }

    private void showConfirmDialog(String callerBtn) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this Meal from "+ callerBtn + "?");


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (callerBtn.equals("Favourites"))
                    presenter.deleteFavoriteMeal(meal);
                else if (callerBtn.equals("Plan"))
                    presenter.deleteMealPlan(meal);
                finish();

            }
        });


        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.myColorPrimaryVariant));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.myColorPrimaryVariant));

    }

}