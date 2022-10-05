package com.example.spotfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomnavigationView;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    FloatingActionButton landmark;
    FloatingActionButton floatingActionButton;
    GoogleMap map;
    public double lat, lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomnavigationView = findViewById(R.id.NavigationView);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionaddBTN);
        landmark = (FloatingActionButton) findViewById(R.id.landmarkbtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the current location
                getCurrentLocation();

            }
        });

        landmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder stringBuilder = new StringBuilder
                        ("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location=" + lat + "," + lng);
                stringBuilder.append("&radius=800000");
                stringBuilder.append("&type=historicallandmark");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=" + getResources().getString(R.string.google_maps_key));

                String url = stringBuilder.toString();
                Object dataFetch[]= new Object[2];

                dataFetch[0] = map;
                dataFetch[1] = url;

                FetchData fetchData = new FetchData();
                fetchData.execute(dataFetch);
            }
        });

        bottomnavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_item:
                        Intent home = new Intent(Home.this, Home.class);
                        startActivity(home);
                        return true;

                    case R.id.person_item:
                        Intent profile = new Intent(Home.this, Profile.class);
                        startActivity(profile);
                        return true;

                    case R.id.settings_item:
                        Intent settings = new Intent(Home.this, Settings.class);
                        startActivity(settings);
                        return true;
                }

                return false;
            }
        });

        //initialize the fuse location
        client = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if(ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //get the current location
            getCurrentLocation();
        }else {
            //when permission denied
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }

    private void getCurrentLocation() {
        if(ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            map = googleMap;
                            LatLng latLng = new LatLng(lat, lng);
                            MarkerOptions options = new MarkerOptions().position(latLng).title("Your Location");
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            map.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }
}

