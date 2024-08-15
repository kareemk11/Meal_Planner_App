package com.example.mealplanner.Authentication.Registeration.RegisterPresenter;


import static android.provider.Settings.System.getString;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.mealplanner.Authentication.Registeration.RegisterView.RegisterView;
import com.example.mealplanner.R.string;
import com.example.mealplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterPresenter implements RegisterPresenterInterface {

    private static final int RC_SIGN_IN = 9001;
    private RegisterView view;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Context context;


    public RegisterPresenter(RegisterView view, Context context) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
        this.mGoogleSignInClient = GoogleSignIn.getClient((Context) view, createGoogleSignInOptions());

    }


    @Override
    public void onGoogleRegisterClicked(String email, String password, String confirmPassword) {

        view.showProgress();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        view.viewGoogleSignIn(signInIntent);
    }

    public void handleSignInResult(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                view.hideProgress();
                view.showError(e.getMessage());

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                view.navigateToMainScreen();
            } else {

                view.showError(task.getException().getMessage());
            }
        });
    }


    @Override
    public void onRegisterClicked(String email, String password, String confirmPassword) {
        view.showProgress();
        validateEmailAndPassword(email, password, confirmPassword);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                view.hideProgress();
                view.navigateToMainScreen();
            } else {
                view.hideProgress();
                view.showError(task.getException().getMessage());
            }

        });
        view.hideProgress();
    }


    public void validateEmailAndPassword(String email, String password, String confirmPassword) {
        boolean isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isValidPassword = password.length() >= 8 && password.matches(".*[!@#$%^&*].*");
        if (TextUtils.isEmpty(email)) {
            view.hideProgress();
            view.showError("Please Enter Email");


        } else if (TextUtils.isEmpty(password)) {
            view.hideProgress();
            view.showError("Please Enter Password");

        } else if (!isValidEmail) {
            view.hideProgress();
            view.showError("Invalid email address");


        } else if (!isValidPassword) {

            view.hideProgress();
            view.showError("Password must be at least 8 characters long and contain at least one special character");

        } else if (!password.equals(confirmPassword)) {
            view.hideProgress();
            view.showError("Passwords do not match");
        }

    }

    private GoogleSignInOptions createGoogleSignInOptions() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id)) // Request ID token
                .requestEmail() // Request email
                .build();
        return gso;
    }


}
