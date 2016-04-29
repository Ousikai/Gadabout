package com.example.sudipta.gadabout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.face.Landmark;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity implements LocationListener, SensorEventListener{
    int curr_map_index;
    TreasureMap curr_map;
    ArrayList<String> clue_desc;
    ArrayList<LatLng> clue_locs;
    double dist = -1000;
    int currClue = 0;
    String dir = "";
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    Button b;
    boolean help_me = false;
    LatLng curLoc = new LatLng(-1000, -1000);

    private ImageView image;

    private float currentDegree = 0f;

    private SensorManager mSensorManager;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        boolean savedGame = getIntent().getBooleanExtra("saved", false);
        if (savedGame){
            curr_map_index = getIntent().getIntExtra("saved_index", -1);
            currClue = getIntent().getIntExtra("saved_clue", 0);
        }
        else{
            curr_map_index = getIntent().getIntExtra("index", -1);
            currClue = 0;
        }


        image = (ImageView) findViewById(R.id.compass);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //SET FONTS
        Typeface tf = Typeface.createFromAsset(getAssets(), "exo.otf");
        TextView clueDesc = (TextView) findViewById(R.id.textView);
        clueDesc.setTypeface(tf);
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setTypeface(tf);
        TextView tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setTypeface(tf);
        TextView tv4 = (TextView) findViewById(R.id.dir);
        tv4.setTypeface(tf);
        Button b1 = (Button) findViewById(R.id.help);
        b1.setTypeface(tf);
        Button b2 = (Button) findViewById(R.id.giveup);
        b2.setTypeface(tf);
        Button b3 = (Button) findViewById(R.id.next);
        b3.setTypeface(tf);
        Button b4 = (Button) findViewById(R.id.back);
        b4.setTypeface(tf);
        //END SET FONTS

        b = (Button) findViewById(R.id.next);
        b.setEnabled(false);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<TreasureMap> allMaps = db.getAllMaps();
        curr_map = allMaps.get(curr_map_index);
        clue_desc = curr_map.get_clue_desc();
        clue_locs = curr_map.get_clue_latlng();
        clueDesc.setText("CURRENT MAP: " + curr_map.get_map_name() + " - " + curr_map.get_map_desc() + "\nCurrent Clue: " + clue_desc.get(currClue));


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void back(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void nextClue(View v) {
        if (currClue < clue_desc.size() - 1) {
            currClue++;
            TextView clueDesc = (TextView) findViewById(R.id.textView);
            clueDesc.setText("CURRENT MAP: " + curr_map.get_map_name() + " - " + curr_map.get_map_desc() + "\nCurrent Clue: " + clue_desc.get(currClue));
            b = (Button) findViewById(R.id.next);
            b.setEnabled(false);
        } else if (currClue >= clue_desc.size() - 1) {
            currClue = 0;
            b.setEnabled(false);
            Intent intent = new Intent(this, DoneGame.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    public void helpMe(View v) {
        if (!help_me){
            help_me=true;
            Button help = (Button) findViewById(R.id.help);
            TextView hlptxt = (TextView) findViewById(R.id.dir);
            help.setText("NO HELP");
            hlptxt.setTextColor(Color.BLACK);
        }
        else if(help_me){
            help_me=false;
            Button help = (Button) findViewById(R.id.help);
            TextView hlptxt = (TextView) findViewById(R.id.dir);
            help.setText("HELP ME!");
            hlptxt.setTextColor(Color.WHITE);
        }

    }
    public void giveUp(View v){

    }

    public void onLocationChanged(Location location) {
        curLoc = new LatLng(location.getLatitude(), location.getLongitude());
        if (curLoc.latitude != -1000 && curLoc.longitude != -1000) {
            float[] res = new float[3];
            Location.distanceBetween(curLoc.latitude, curLoc.longitude, clue_locs.get(currClue).latitude, clue_locs.get(currClue).longitude, res);
            if (res[0] < 15) {
                TextView wrongRight = (TextView) findViewById(R.id.textView2);
                wrongRight.setText("YOU'RE RIGHT!");
                b.setEnabled(true);
            } else {
                TextView wrongRight = (TextView) findViewById(R.id.textView2);
                wrongRight.setText("NOPE, NOT QUITE.");
            }

            if (Math.abs(dist - res[0]) >= 2) {
                TextView hotCold = (TextView) findViewById(R.id.textView3);
                if (Math.abs(dist) > Math.abs(res[0])) {
                    hotCold.setText("Getting Warmer! \n" + curLoc);
                } else {
                    hotCold.setText("Getting Colder! \n" + curLoc);
                }
                dist = res[0];
            }

            getDir(curLoc, clue_locs.get(currClue));
        }


    }

    public void getDir(LatLng cur, LatLng fin){
        String cardinal = "";
        if (cur.latitude < fin.latitude){
            cardinal+="north";
        }
        else if (cur.latitude > fin.latitude){
            cardinal+="south";
        }
        else{
            cardinal+="";
        }

        if (cur.longitude < fin.longitude){
            cardinal += "east";
        }
        else if (cur.longitude > fin.longitude){
            cardinal += "west";
        }

        dir = "Go " + cardinal+".";
        TextView needHelp = (TextView) findViewById(R.id.dir);
        needHelp.setText(dir);
    }




    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GamePlay Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sudipta.gadabout/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GamePlay Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sudipta.gadabout/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
        saveGame();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        saveGame();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float degree = Math.round(event.values[0]);
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        ra.setDuration(210);
        ra.setFillAfter(true);

        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onBackPressed() {
        saveGame();
        super.onBackPressed();
    }

    public void saveGame(){
        if(currClue>0 && currClue < clue_desc.size()-1){
            SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("saved", true);
            editor.putInt("saved_map", curr_map_index);
            editor.putInt("saved_clue", currClue);
            editor.apply();
            System.out.println("Game act. saved: " + settings.getBoolean("saved", false));
        }
    }
}