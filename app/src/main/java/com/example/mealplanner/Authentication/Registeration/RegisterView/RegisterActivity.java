package com.example.mealplanner.Authentication.Registeration.RegisterView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.Authentication.Registeration.RegisterPresenter.RegisterPresenter;
import com.example.mealplanner.Database.MealsLocalDataSource;
import com.example.mealplanner.Database.Model.User.User;
import com.example.mealplanner.HomeActivity.HomeActivity;
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

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "EmailPassword";
    TextInputEditText emailTxt;
    TextInputEditText passTxt;
    TextInputEditText confirmPassTxt;
    TextInputEditText usernameTxt;
    TextView havingAccountTxt;
    Button registerBtn;
    ProgressBar progressBar;
    Button googleBtn;
    private FirebaseAuth mAuth;
    private RegisterPresenter registerPresenter;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    UserSession userSession;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser currentUser;



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
        mGoogleSignInClient = GoogleSignIn.getClient(this, createGoogleSignInOptions());
        mAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> {
            currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                registerPresenter.onUserLoggedIn(currentUser);
            } else {

                Log.d(TAG, "No user is signed in");
            }
        };
        registerPresenter = new RegisterPresenter(this ,mGoogleSignInClient,
                Repository.getInstance(MealsRemoteDataScource.getInstance(),MealsLocalDataSource.getInstance(this)));
        setContentView(R.layout.activity_register);

        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passTxt);
        registerBtn = findViewById(R.id.registerBtn);
        googleBtn = findViewById(R.id.google_btn);
        progressBar = findViewById(R.id.progressBar);
        usernameTxt = findViewById(R.id.usernameTxt);
        confirmPassTxt = findViewById(R.id.confirmPassTxt);
        havingAccountTxt = findViewById(R.id.accountTxt);
        havingAccountTxt.setClickable(true);
        havingAccountTxt.setTypeface(null, Typeface.BOLD);
        havingAccountTxt.setPaintFlags(havingAccountTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        progressBar.setVisibility(View.GONE);

        googleBtn.setOnClickListener(view -> {
            registerPresenter.onGoogleRegisterClicked(emailTxt.getText().toString(), passTxt.getText().toString(), confirmPassTxt.getText().toString());

        });


        havingAccountTxt.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });



        registerBtn.setOnClickListener(view -> {

            String username = usernameTxt.getText().toString();
            registerPresenter.onRegisterClicked(emailTxt.getText().toString(), passTxt.getText().toString(), confirmPassTxt.getText().toString());
        });
    }
    public void viewGoogleSignIn(Intent signInIntent) {

        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        registerPresenter.handleSignInResult(requestCode,data);
    }




    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authStateListener);


        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            registerPresenter.onUserLoggedIn(currentUser);
        }


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
        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
        finish();
    }

    private GoogleSignInOptions createGoogleSignInOptions() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        return gso;
    }


}

//    public void transferToApp(FirebaseUser user) {
//        if (user != null) {
//            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//            finish();
//        } else {
//            Toast.makeText(RegisterActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }