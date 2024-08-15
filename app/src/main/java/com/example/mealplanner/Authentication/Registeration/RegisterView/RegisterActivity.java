package com.example.mealplanner.Authentication.Registeration.RegisterView;

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

import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.Authentication.Registeration.RegisterPresenter.RegisterPresenter;
import com.example.mealplanner.HomeActivity.HomeActivity;
import com.example.mealplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements RegisterView{

    private static final int RC_SIGN_IN = 9001;
    TextInputEditText emailTxt;
    TextInputEditText passTxt;
    TextInputEditText confirmPassTxt;
    TextInputEditText usernameTxt;
    TextView havingAccountTxt;;
    Button registerBtn;
    ProgressBar progressBar;
    ImageView googleBtn;
    ImageView facebook_image;
    private FirebaseAuth mAuth;
    private RegisterPresenter registerPresenter;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        registerPresenter = new RegisterPresenter(this , this);
        setContentView(R.layout.activity_register);

        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passTxt);
        registerBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressBar);
        usernameTxt = findViewById(R.id.usernameTxt);
        confirmPassTxt = findViewById(R.id.confirmPassTxt);
        havingAccountTxt = findViewById(R.id.accountTxt);
        googleBtn = findViewById(R.id.google_image);
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

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        registerPresenter.handleSignInResult(requestCode,data);
    }




    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
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