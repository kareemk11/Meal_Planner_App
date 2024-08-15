package com.example.mealplanner.Authentication.Registeration.RegisterView;

import android.content.Intent;

public interface RegisterView {
    void showProgress();
    void hideProgress();
    void showError(String errorMessage);
    void navigateToMainScreen();

    void viewGoogleSignIn(Intent signInIntent);

//    void transferToApp(FirebaseUser user);
}
