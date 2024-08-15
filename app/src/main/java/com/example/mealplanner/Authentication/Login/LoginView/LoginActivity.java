package com.example.mealplanner.Authentication.Login.LoginView;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner.Authentication.Login.LoginPresenter.LoginPresenter;
import com.example.mealplanner.Authentication.Registeration.RegisterView.RegisterActivity;
import com.example.mealplanner.RandomMeal.RandomMealActivity;
import com.example.mealplanner.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = "EmailPassword";
    private static final int RC_SIGN_IN = 9001;
    TextInputEditText emailTxt;
    TextInputEditText passTxt;
    Button loginBtn;
    ProgressBar progressBar;
    TextView noAccountTxt;
    ImageView google_image;
    ImageView facebook_image;
    private FirebaseAuth mAuth;
    private LoginPresenter loginPresenter;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, RandomMealActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passTxt);
        loginBtn = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        noAccountTxt = findViewById(R.id.accountTxt);
        google_image = findViewById(R.id.google_image);
        facebook_image = findViewById(R.id.facebook_image);
        noAccountTxt.setClickable(true);
        noAccountTxt.setTypeface(null, Typeface.BOLD);
        noAccountTxt.setPaintFlags(noAccountTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        loginPresenter = new LoginPresenter(this, this);
        noAccountTxt.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });


        google_image.setOnClickListener(view -> {
            loginPresenter.onGoogleLoginClicked(emailTxt.getText().toString(), passTxt.getText().toString());
        });


        loginBtn.setOnClickListener(view ->
        {
            String email = emailTxt.getText().toString();
            String password = passTxt.getText().toString();
            loginPresenter.onLoginClicked(email, password);
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
        startActivity(new Intent(LoginActivity.this, RandomMealActivity.class));
        finish();

    }

    @Override
    public void viewGoogleSignIn(Intent signInIntent) {
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        loginPresenter.handleSignInResult(requestCode, data);
    }
}