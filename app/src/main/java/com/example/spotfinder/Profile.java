package com.example.spotfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    Button logoutBtn;
    BottomNavigationView bottomnavigationView;
    TextView emailT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseAuth = FirebaseAuth.getInstance();
        bottomnavigationView = findViewById(R.id.NavigationView1);
        emailT = findViewById(R.id.textView2);

        bottomnavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_item:
                        Intent home = new Intent(Profile.this, Home.class);
                        startActivity(home);
                        return true;

                    case R.id.person_item:
                        Intent profile = new Intent(Profile.this, Profile.class);
                        startActivity(profile);
                        return true;

                    case R.id.settings_item:
                        Intent settings = new Intent(Profile.this, Settings.class);
                        startActivity(settings);
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser != null){
            //there is some user logged in
            emailT.setText(mFirebaseUser.getEmail());
        }
        else{
            //no one is logged in
            startActivity(new Intent(this, Login.class));
            finish();
        }
    }

    public void logout(View view) {
        mFirebaseAuth.signOut();
        Intent gotoLogin = new Intent(Profile.this, Login.class);
        startActivity(gotoLogin);
        Toast.makeText(Profile.this, "Logged Out", Toast.LENGTH_SHORT).show();
    }
}