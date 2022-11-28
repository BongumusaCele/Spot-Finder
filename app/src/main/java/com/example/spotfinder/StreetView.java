package com.example.spotfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;
import com.google.android.gms.maps.model.StreetViewSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StreetView extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {

    private StreetViewPanorama streetViewPanorama;
    private boolean secondLocation=false;
    FloatingActionButton mystreet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);
        SupportStreetViewPanoramaFragment supportStreetViewPanoramaFragment = (SupportStreetViewPanoramaFragment)getSupportFragmentManager().findFragmentById(R.id.googlemapstreetview);

        supportStreetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
        mystreet = (FloatingActionButton) findViewById(R.id.btn_mystreet);

        mystreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondLocation =! secondLocation;
                onStreetViewPanoramaReady(streetViewPanorama);
            }
        });

    }

    @Override
    public void onStreetViewPanoramaReady(@NonNull StreetViewPanorama streetViewPanorama) {
        streetViewPanorama = streetViewPanorama;

        if(secondLocation){
            streetViewPanorama.setPosition(new LatLng(-26.1925666, 28.0316998), StreetViewSource.OUTDOOR);

        }
        else{
            streetViewPanorama.setPosition(new LatLng(-26.1925666, 28.0316998));
        }

        streetViewPanorama.setStreetNamesEnabled(true);
        streetViewPanorama.setPanningGesturesEnabled(true);
        streetViewPanorama.setZoomGesturesEnabled(true);
        streetViewPanorama.setUserNavigationEnabled(true);
        streetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().orientation(new StreetViewPanoramaOrientation(20,20))
                        .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                        .build(),2000
        );

        streetViewPanorama.setOnStreetViewPanoramaCameraChangeListener(panoramachangelistner);
    }

    private StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener panoramachangelistner = new StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener() {
        @Override
        public void onStreetViewPanoramaCameraChange(@NonNull StreetViewPanoramaCamera streetViewPanoramaCamera) {
            Toast.makeText(StreetView.this, "Location Updated", Toast.LENGTH_SHORT).show();
        }
    };
}