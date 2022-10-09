package com.example.spotfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Directions extends AppCompatActivity {

    //Initialize variables
    EditText currentLocation, Destination;
    Button directions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //Assign variables
        currentLocation = findViewById(R.id.ed_startlocation);
        Destination = findViewById(R.id.ed_destination);
        directions = findViewById(R.id.getdirectionsbtn);

        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get value from edit text
                String location = currentLocation.getText().toString().trim();
                String destination = Destination.getText().toString().trim();

                //Check condition
                if(location.equals("") && destination.equals("")){
                    //When both value blank
                    Toast.makeText(getApplicationContext(), "Enter both location", Toast.LENGTH_SHORT).show();
                    
                }else{
                    //When both value full
                    
                    //Display Track
                    DisplayTrack(location,destination);
                }
            }
        });
    }

    private void DisplayTrack(String location, String destination) {
        //If the device does not have a map installed, then redirect it to play store
        try {
            //When google map is installed
            //Initialize uri
            Uri uri1 = Uri.parse("https://www.google.com/maps/dir/" + location +
                    "/" + destination);

            //Initialize intent with action view
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri1);

            //Set package
            intent.setPackage("com.google.android.app.maps");

            //Set Flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Start activity
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            //When google map is not installed
            //Initialize uri
            Uri uri2 = Uri.parse("https://www.google.com/maps/dir/" + location +
                    "/" + destination);

            //Initialize intent with action view
            Intent intent2 = new Intent(android.content.Intent.ACTION_VIEW, uri2);

            //Set flag
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Start Activity
            startActivity(intent2);
        }
    }
}