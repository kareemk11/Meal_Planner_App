package com.example.mealplanner.LocalMeal.Presenter;

import com.example.mealplanner.Database.Model.LocalMeal.LocalMeal;
import com.example.mealplanner.LocalMeal.View.LocalMealView;
import com.example.mealplanner.Model.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalMealPresenter {
    private LocalMealView view;
    private Repository repository;

    public LocalMealPresenter(LocalMealView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }
    public void deleteFavoriteMeal(LocalMeal meal) {
        repository.deleteFavorite(meal);
    }
    public void deleteMealPlan(LocalMeal meal) {
        repository.deleteMealDate(meal);
    }

    public String extractYouTubeId(String videoUrl) {
        String videoId = null;
        String pattern = "^(http(s)?:\\/\\/)?(www\\.)?(youtube\\.com|youtu\\.?be)\\/(watch\\?v=|embed\\/|v\\/|.+\\?v=)?([^&=%\\?]{11})";

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(videoUrl);

        if (matcher.find()) {
            videoId = matcher.group(6);
        }

        return videoId;
    }
}
