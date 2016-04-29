package com.example.sudipta.gadabout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.Manifest;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener{

    // Set and check permission variables
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    // GoogleMap Object
    private GoogleMap mMap;

    //Set up Treasure Map object for submitting into database
    private int clue_limit = 0;
    private ArrayList<String> clue_desc = new ArrayList<String>();
    TreasureMap newMap = new TreasureMap("not set","not set","not set","not set","not set","not set","not set");

    private double[] curPos = {0,0};
    private double[] lastPos = {0,0};
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
        final Button button_clue = (Button) findViewById(R.id.set_clue);
        button_clue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addMark();
            }
        });

        //Stop keyboard from popping up each time the activity opens
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Set fancy font!
        Typeface tf = Typeface.createFromAsset(getAssets(), "exo.otf");
        Button b1 = (Button)findViewById(R.id.save);
        b1.setTypeface(tf);
        Button b2 = (Button)findViewById(R.id.set_clue);
        b2.setTypeface(tf);
        Button b3 = (Button)findViewById(R.id.back);
        b3.setTypeface(tf);
        EditText et= (EditText) findViewById(R.id.editText);
        et.setTypeface(tf);

    }

    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void saveMap(View v){
        if (newMap.get_clue0().equals("not set")){
            Toast.makeText(this, "Set one clue before saving the map!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, SaveMap.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("savedMap", newMap);
            startActivity(intent);
        }
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
        //mMap.getUiSettings().setAllGesturesEnabled(true);
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
                mMap.addMarker(new MarkerOptions().
                        position(position).
                        alpha(0.7f).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                Context context = getApplicationContext();
                //Toast.makeText(context, position + " : " + position.longitude, Toast.LENGTH_SHORT).show();
                curPos[0] = position.latitude;
                curPos[1] = position.longitude;
            }
        });
    }

    public void addMark(){
        EditText txt = (EditText)findViewById(R.id.editText);
        String clueText= txt.getText().toString();
        if (!clueText.equals("")&&curPos[0]!=lastPos[0]&&curPos[1]!=lastPos[1]&&(clue_limit<5)){
            clue_desc.add(clueText);
            String setClueStr = clueText + ";" + curPos[0] + ";" + curPos[1];
            newMap.setAnyClue(clue_limit, setClueStr);
            allPos.add(new LatLng(curPos[0], curPos[1]));
            mMap.clear();
            addAll();
            txt.setText("");
            clue_limit++;
            lastPos[0]=curPos[0];
            lastPos[1]=curPos[1];
            Toast.makeText(this, "Clue added!", Toast.LENGTH_SHORT).show();
        }
        else{
            if (clue_limit==5){
                Toast.makeText(this, "You have set the max amount of clues!", Toast.LENGTH_SHORT).show();}
            else if (clueText.equals("")){
                Toast.makeText(this, "Write a hint for your clue!", Toast.LENGTH_SHORT).show();}
            else if (curPos[0]==0&&curPos[1]==0){
                Toast.makeText(this, "Choose a set of coordinates!", Toast.LENGTH_SHORT).show();}
            else{
                Toast.makeText(this, "Choose a different set of coordinates!", Toast.LENGTH_SHORT).show();}
        }
    }

    public void addAll(){
        int i = 0;
        for (LatLng point : allPos) {
            int j = i+1;
            options.position(point);
            options.title("Clue "+ j);
            options.snippet(clue_desc.get(i));
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(options);
            i++;
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

    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

