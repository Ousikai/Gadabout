package com.example.sudipta.gadabout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.face.Landmark;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity implements LocationListener{
    int curr_map_index;
    TreasureMap curr_map;
    LatLng[]clues = new LatLng[3];
    double dist = -1000;
    int currClue=0;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    Button b;
    LatLng curLoc = new LatLng(-1000,-1000);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        b=(Button) findViewById(R.id.next);
        b.setEnabled(true);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        curr_map_index = getIntent().getIntExtra("index", -1);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<TreasureMap> allMaps = db.getAllMaps();
        curr_map = allMaps.get(curr_map_index);
        String[]clue0 = curr_map.get_clue0().split(";");
        LatLng clue0_xy = new LatLng(Double.parseDouble(clue0[0]), Double.parseDouble(clue0[1]));
        String[]clue1 = curr_map.get_clue1().split(";");
        LatLng clue1_xy = new LatLng(Double.parseDouble(clue1[0]), Double.parseDouble(clue1[1]));
        System.out.println(clue1_xy);
        String[]clue2 = curr_map.get_clue2().split(";");
        LatLng clue2_xy = new LatLng(Double.parseDouble(clue2[0]), Double.parseDouble(clue2[1]));
        System.out.println(clue2_xy);
        clues[0] = clue0_xy;
        clues[1] = clue1_xy;
        clues[2] = clue2_xy;
        TextView clueDesc = (TextView) findViewById(R.id.textView);
        clueDesc.setText("CURRENT MAP: " + curr_map.get_map_name() + " - " + curr_map.get_map_desc() + "\n Current Clue: " + currClue);


    }
    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void nextClue(View v){
        if (currClue>=2) {
            currClue=0;
            b.setEnabled(false);
            Intent intent = new Intent(this, DoneGame.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        else if (currClue==0){
            currClue ++;
            System.out.println(clues[currClue]);
            TextView clueDesc = (TextView) findViewById(R.id.textView);
            clueDesc.setText("CURRENT MAP: " + curr_map.get_map_name() + " - " + curr_map.get_map_desc() + "\n Current Clue: " + currClue);
        }
        else if (currClue==1){
            currClue ++;
            TextView clueDesc = (TextView) findViewById(R.id.textView);
            clueDesc.setText("CURRENT MAP: " + curr_map.get_map_name() + " - " + curr_map.get_map_desc() + "\n Current Clue: " + currClue);
        }
    }

    public void checkHere(View v){

    }
    public void onLocationChanged(Location location) {
        curLoc = new LatLng(location.getLatitude(),location.getLongitude());
        if (curLoc.latitude!=-1000 && curLoc.longitude!=-1000) {
            float[] res = new float[3];
            Location.distanceBetween(curLoc.latitude, curLoc.longitude, clues[currClue].latitude, clues[currClue].longitude, res);
            if (res[0] < 15) {
                TextView wrongRight = (TextView) findViewById(R.id.textView2);
                wrongRight.setText("You're Right!");
                b.setEnabled(true);
            } else {
                TextView wrongRight = (TextView) findViewById(R.id.textView2);
                wrongRight.setText("Wrong!");
            }

            if(Math.abs(dist-res[0])>=2){
                TextView hotCold = (TextView) findViewById(R.id.textView3);
                if (Math.abs(dist)>Math.abs(res[0])){
                    hotCold.setText("Getting Warmer! \n" + curLoc);
                }
                else{
                    hotCold.setText("Getting Colder! \n" + curLoc);
                }
                dist = res[0];
            }
        }




    }
    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

}