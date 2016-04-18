package com.treasurehunt.dinobros.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.pm.PackageManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    // Set and check permission variables
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    // GoogleMap Object
    private GoogleMap mMap;

    // Clue Management Variables
    int clue_counter = 1;

    private double[] curPos = {0,0};
    private ArrayList<LatLng> allPos = new ArrayList<LatLng>();
    private MarkerOptions options = new MarkerOptions();

    String data1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Button for creating clues
        final Button button_clue = (Button) findViewById(R.id.button_clue);
        button_clue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addMark();
            }
        });

        //Button for finalizing map
        final Button button_submit = (Button) findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                writeToFile(data1);
                mMap.clear();
            }
        });

        //Button for going to a random place
        final Button button_random = (Button) findViewById(R.id.button_random);
        button_random.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MapsActivity.this, database_sandbox.class);
                MapsActivity.this.startActivity(myIntent);
            }
        });

        // Other fun stuff

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Initialize Google Map according to user current location
        enableMyLocation();
        initializeMap();

        //Create Clues
        newClue();

    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // but why doe
        }
    }

    public void initializeMap() {
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((currentLocation),
                        18.0f));
            }
        });

    }

    public void newClue(){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng position) {
                mMap.clear();
                addAll();
                String clue_name = "Clue number " + clue_counter;
                clue_counter++;
                mMap.addMarker(new MarkerOptions().
                        position(position).
                        title(clue_name).
                        alpha(0.7f).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                Context context = getApplicationContext();
                Toast.makeText(context, position + " : " + position.longitude, Toast.LENGTH_SHORT).show();
                curPos[0] = position.latitude;
                curPos[1] = position.longitude;
            }
        });
    }

    public void pingCurrentLocation(){
        //Currently pending
    }

    public void addMark(){
        allPos.add(new LatLng(curPos[0], curPos[1]));
        EditText txt   = (EditText)findViewById(R.id.clue_info);
        data1 +=  txt.getText() + "," +curPos[0] + "," + curPos[1]+";";
        mMap.clear();
        addAll();

    }

    public void addAll(){
        for (LatLng point : allPos) {
            options.position(point);
            options.title("");
            options.snippet("");
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(options);
        }
    }

    public void testToast(){
        Toast.makeText(this, "Mom's spaghetti", Toast.LENGTH_SHORT).show();
    }

    private void writeToFile(String data) {
        try {
            Context context = getApplicationContext();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("map.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //Toast.makeText(this, "Mom's spaghetti", Toast.LENGTH_SHORT).show();
    //LatLng sydney = new LatLng(-34, 151);
    //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

}

