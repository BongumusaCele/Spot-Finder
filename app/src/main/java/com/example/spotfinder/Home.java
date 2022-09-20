package com.example.spotfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomnavigationView;
    NavigationView navigation;

    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getting element by ID
        bottomnavigationView = findViewById(R.id.NavigationView);
        //navigation = findViewById(R.menu.navigationView,menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, homeFragment).commit();


        bottomnavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, homeFragment).commit();
                        return true;

                    case R.id.person_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, profileFragment).commit();
                        return true;

                    case R.id.settings_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, settingsFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}