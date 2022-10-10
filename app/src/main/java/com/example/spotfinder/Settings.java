package com.example.spotfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

RecyclerView rv;
private ArrayList<FavouriteLandmarks>landmarkList;

    BottomNavigationView bottomnavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        rv = findViewById(R.id.recycle_view);
        landmarkList = new ArrayList<>();


        setLandmarkList();
        setAdapter();



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
    private void setAdapter(){
        RecyclerAdapter adapter = new RecyclerAdapter(landmarkList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    public void setLandmarkList(){
        landmarkList.add(new FavouriteLandmarks("Joburg Theatre"));
        landmarkList.add(new FavouriteLandmarks("Museum Africa"));
        landmarkList.add(new FavouriteLandmarks("Constitutional Hill Human Rights Precinct Hall"));

    }
}