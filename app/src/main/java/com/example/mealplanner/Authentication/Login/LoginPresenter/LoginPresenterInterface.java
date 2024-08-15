package com.example.mealplanner.Authentication.Login.LoginPresenter;

import android.content.Intent;

public interface LoginPresenterInterface {

    void onLoginClicked(String email, String password);
    void onGoogleLoginClicked(String email, String password);
    void handleSignInResult(int requestCode, Intent data);
}

