package com.example.mealplanner.DataBackup;

import com.example.mealplanner.Database.Model.Favourite.FavouriteMeal;
import com.example.mealplanner.Database.Model.MealDate.MealDate;
import com.example.mealplanner.FavouriteMeals.Presenter.DatabaseEventListener;
import com.example.mealplanner.FavouriteMeals.Presenter.MealLoadListener;
import com.example.mealplanner.FavouriteMeals.Presenter.SaveMealListener;
import com.example.mealplanner.MealsPlan.Presenter.DatedMealsLoadedListener;
import com.example.mealplanner.Model.Meal.Meal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseSyncManager {

    private static FirebaseSyncManager instance;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore db;


    private FirebaseSyncManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized FirebaseSyncManager getInstance() {
        if (instance == null) {
            instance = new FirebaseSyncManager();
        }
        return instance;
    }

    public void saveFavoriteMeals(List<FavouriteMeal> meals, SaveMealListener listener) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            for (FavouriteMeal meal : meals) {
                String mealId = meal.getMealId();
                db.collection("users")
                        .document(userId)
                        .collection("favoriteMeals")
                        .document(mealId)
                        .set(meal)
                        .addOnSuccessListener(documentReference -> {
                            listener.onSaveMealSuccess();
                        })
                        .addOnFailureListener(e -> {
                            listener.onSaveMealFailure(e.getMessage());
                        });
            }
        }
    }

    public void saveDatedMeals(List<MealDate> meals, SaveMealListener listener) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            for (MealDate meal : meals) {
                String mealId = meal.getMealId();
                db.collection("users")
                        .document(userId)
                        .collection("mealDate")
                        .document(mealId).set(meal)
                        .addOnSuccessListener(documentReference -> {
                            listener.onSaveMealSuccess();
                        })
                        .addOnFailureListener(e -> {
                            listener.onSaveMealFailure(e.getMessage());
                        });
            }
        }
    }


    public void loadMeals(MealLoadListener listener) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            db.collection("users").document(userId).collection("favoriteMeals")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<FavouriteMeal> meals = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FavouriteMeal meal = document.toObject(FavouriteMeal.class);
                                meals.add(meal);
                            }
                            listener.onMealsLoaded(meals);
                        } else {

                        }
                    });
        }
    }

    public void loadDatedMeals(DatedMealsLoadedListener listener) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            db.collection("users").document(userId).collection("mealDate")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<MealDate> meals = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MealDate meal = document.toObject(MealDate.class);
                                meals.add(meal);
                            }
                            listener.onMealsLoaded(meals);

                        } else {
                            listener.onMealLoadFailed(task.getException().getMessage());
                        }

                    });
        }
    }

}

