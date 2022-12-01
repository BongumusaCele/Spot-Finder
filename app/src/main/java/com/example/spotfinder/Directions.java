package com.example.spotfinder;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Directions extends AppCompatActivity {

    //Initialize variables
    EditText currentLocation, Destination;
    Button directions;
    Button saveLocation;

    FirebaseDatabase savedLandmarksDb;
    public static DatabaseReference myRef, databaseReference;

    public static List<String> landmarks = new ArrayList<>();

    DownloadUrl placeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //Assign variables
        currentLocation = findViewById(R.id.ed_startlocation);
        Destination = findViewById(R.id.ed_destination);
        directions = findViewById(R.id.getdirectionsbtn);
        saveLocation = findViewById(R.id.savelocationsbtn);

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

        savedLandmarksDb = FirebaseDatabase.getInstance("https://console.firebase.google.com/u/2/project/spot-finder-ac6ba/database/spot-finder-ac6ba-default-rtdb/data/~2FLandmark");
        databaseReference = savedLandmarksDb.getReference("Landmark");


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Home h = new Home();
                Map <String,String> map = (Map<String,String>)snapshot.getValue();

                landmarks.add(map.get("Destination"));


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        placeInfo = new DownloadUrl();

        saveLocation = findViewById(R.id.savelocationsbtn);

        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = savedLandmarksDb.getReference("Landmark").child(landmarks.size()+"");

                //get name of landmark from 'get destination' textfield
                String landmark = Destination.getText().toString();

                if(TextUtils.isEmpty(landmark)){
                    Toast.makeText(getApplicationContext(),"Enter destination to be saved", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child(landmark).setValue(placeInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Landmark has been added to 'Favourites'",Toast.LENGTH_SHORT).show();
                        }
                    });
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