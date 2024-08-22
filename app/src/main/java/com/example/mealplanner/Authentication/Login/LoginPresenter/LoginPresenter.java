package com.example.mealplanner.Authentication.Login.LoginPresenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.example.mealplanner.Authentication.Login.LoginView.LoginView;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Database.Model.User.User;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPresenter implements LoginPresenterInterface {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG ="LoginPresenter" ;
    private LoginView view;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    UserSession userSession;
    Repository repository;


    public LoginPresenter(LoginView view , GoogleSignInClient mGoogleSignInClient,Repository repository) {
        this.view = view;
        this.repository = repository;
        mAuth = FirebaseAuth.getInstance();
        this.mGoogleSignInClient = mGoogleSignInClient;
    }

    public void onLoginClicked(String email, String password) {
        view.showProgress();

        validateEmailAndPassword(email, password);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            view.hideProgress();
            if (task.isSuccessful()) {
                view.hideProgress();
                onUserLoggedIn(mAuth.getCurrentUser());
            } else {
                view.hideProgress();
                view.showError(task.getException().getMessage());
            }
        });
    }

    public void onGoogleLoginClicked(String email, String password) {

        view.showProgress();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        view.viewGoogleSignIn(signInIntent);
        view.hideProgress();
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
                onUserLoggedIn(user);
            } else {

                view.showError(task.getException().getMessage());
            }
        });
    }

    private void validateEmailAndPassword(String email, String password) {
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
        }
    }



    public void onUserLoggedIn(FirebaseUser currentUser) {
        User user = new User();
        user.setEmail(currentUser.getEmail());
        user.setUsername(currentUser.getDisplayName());
        user.setUserId(currentUser.getUid());
        user.setGoogleUserId(currentUser.getProviderId());


        userSession = UserSession.getInstance();
        userSession.setUid(currentUser.getUid());
        userSession.setEmail(currentUser.getEmail());
        userSession.setUsername(currentUser.getDisplayName());
        userSession.setGuest(false);

        Log.i(TAG, "onCreate: "+ UserSession.getInstance().getGuest()+" "+UserSession.getInstance().getUid());

        repository.insertUser(user);

        view.navigateToMainScreen();
    }

    public void onGuestLoginClicked() {
        userSession = UserSession.getInstance();
        userSession.setGuest(true);
        view.navigateToMainScreen();
    }
}
