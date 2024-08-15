package com.example.mealplanner.Authentication.Registeration.RegisterPresenter;

import android.content.Intent;

public interface RegisterPresenterInterface {
    void onRegisterClicked(String email, String password, String confirmPassword);
    void onGoogleRegisterClicked(String email, String password, String confirmPassword);
    void handleSignInResult(int requestCode, Intent data);
}
