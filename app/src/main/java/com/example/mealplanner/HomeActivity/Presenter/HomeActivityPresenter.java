package com.example.mealplanner.HomeActivity.Presenter;

import com.example.mealplanner.HomeActivity.View.HomeActivityView;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

public class HomeActivityPresenter {
    private HomeActivityView view;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseUser user;

    public HomeActivityPresenter(HomeActivityView view, GoogleSignInClient mGoogleSignInClient) {
        this.view = view;
        this.mGoogleSignInClient = mGoogleSignInClient;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void onLogoutClicked() {
        if (user != null) {
            boolean isGoogleSignIn = false;
            for (UserInfo userInfo : user.getProviderData()) {
                if (GoogleAuthProvider.PROVIDER_ID.equals(userInfo.getProviderId())) {
                    isGoogleSignIn = true;
                    break;
                }
            }

            mAuth.signOut();
            if (isGoogleSignIn) {
                mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        view.navigateToLoginScreen();
                    } else {
                        view.showError("Sign out failed");
                    }
                });
            } else {
                view.navigateToLoginScreen();
                }
        }


    }
}
