package com.example.mealplanner.HomeActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mealplanner.Authentication.Login.LoginView.LoginActivity;
import com.example.mealplanner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton logoutBtn;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.logoutBtn);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        logoutBtn.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            mAuth.signOut();
            finish();
        });
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_nav_view);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


        NavigationUI.setupWithNavController(bottomNavView, navController);
    }
}




