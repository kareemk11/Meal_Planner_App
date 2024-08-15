package com.example.mealplanner.Authentication.Login.LoginView;

import android.content.Intent;

public interface LoginView {

    void showProgress();
    void hideProgress();
    void showError(String errorMessage);
    void navigateToMainScreen();
    void viewGoogleSignIn(Intent signInIntent);
}
