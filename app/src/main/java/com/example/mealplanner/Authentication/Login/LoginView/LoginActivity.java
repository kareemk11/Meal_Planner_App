package com.example.mealplanner.Authentication.Login.LoginView;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner.Authentication.Login.LoginPresenter.LoginPresenter;
import com.example.mealplanner.Authentication.Registeration.RegisterView.RegisterActivity;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.HomeActivity.View.HomeActivity;
import com.example.mealplanner.Model.Repository;
import com.example.mealplanner.Model.UserSession;
import com.example.mealplanner.Network.MealsRemoteDataScource;
import com.example.mealplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = "EmailPassword";
    private static final int RC_SIGN_IN = 9001;
    TextInputEditText emailTxt;
    TextInputEditText passTxt;
    Button loginBtn;
    Button guestBtn;
    ProgressBar progressBar;
    TextView noAccountTxt;
    Button google_image;
    private FirebaseAuth mAuth;
    private LoginPresenter loginPresenter;
    UserSession userSession;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser currentUser;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loginPresenter.onUserLoggedIn(currentUser);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, createGoogleSignInOptions());

        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                loginPresenter.onUserLoggedIn(currentUser);
            } else {

                Log.d(TAG, "No user is signed in");
            }
        };
        setContentView(R.layout.activity_login);
        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passTxt);
        loginBtn = findViewById(R.id.loginBtn);
        guestBtn = findViewById(R.id.guest_btn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        noAccountTxt = findViewById(R.id.accountTxt);
        google_image = findViewById(R.id.google_btn);
        noAccountTxt.setClickable(true);
        noAccountTxt.setTypeface(null, Typeface.BOLD);
        noAccountTxt.setPaintFlags(noAccountTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        loginPresenter = new LoginPresenter(this, mGoogleSignInClient,
                Repository.getInstance(MealsRemoteDataScource.getInstance(),MealsLocalDataSource.getInstance(this)));
        noAccountTxt.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });


        google_image.setOnClickListener(view -> {
            loginPresenter.onGoogleLoginClicked(emailTxt.getText().toString(), passTxt.getText().toString());
        });


        loginBtn.setOnClickListener(view ->
        {
            loginPresenter.onLoginClicked(emailTxt.getText().toString(), passTxt.getText().toString());
        });
        guestBtn.setOnClickListener(view -> {
            loginPresenter.onGuestLoginClicked();
        });
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();

    }

    @Override
    public void viewGoogleSignIn(Intent signInIntent) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.handleSignInResult(requestCode, data);
    }

    private GoogleSignInOptions createGoogleSignInOptions() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        return gso;
    }

}