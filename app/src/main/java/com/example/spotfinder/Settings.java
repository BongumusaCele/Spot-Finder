package com.example.spotfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Settings extends AppCompatActivity {

    BottomNavigationView bottomnavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bottomnavigationView = findViewById(R.id.NavigationView2);

        bottomnavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_item:
                        Intent home = new Intent(Settings.this, Home.class);
                        startActivity(home);
                        return true;

                    case R.id.person_item:
                        Intent profile = new Intent(Settings.this, Profile.class);
                        startActivity(profile);
                        return true;

                    case R.id.settings_item:
                        Intent settings = new Intent(Settings.this, Settings.class);
                        startActivity(settings);
                        return true;
                }

                return false;
            }
        });
    }
}