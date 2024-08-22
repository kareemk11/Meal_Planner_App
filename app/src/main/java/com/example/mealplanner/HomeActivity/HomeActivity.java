package com.example.mealplanner.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

public class HomeActivity extends AppCompatActivity implements HomeActivityView{

    FloatingActionButton logoutBtn;
    FirebaseAuth mAuth;
    FirebaseUser user;
    HomeActivityPresenter presenter;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logoutBtn = findViewById(R.id.logoutBtn);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mGoogleSignInClient = GoogleSignIn.getClient(this, createGoogleSignInOptions());
        presenter = new HomeActivityPresenter(this, mGoogleSignInClient);
        logoutBtn.setOnClickListener(view -> {

            presenter.onLogoutClicked();
        });

        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        NavigationUI.setupWithNavController(bottomNavView, navController);
    }

    public void navigateToLoginScreen() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void showError(String signOutFailed) {
        Toast.makeText(this, signOutFailed, Toast.LENGTH_SHORT).show();
    }

    private GoogleSignInOptions createGoogleSignInOptions() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        return gso;
    }
}




