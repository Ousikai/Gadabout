package com.example.sudipta.gadabout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set fancy fonts!
        Typeface tf = Typeface.createFromAsset(getAssets(), "exo.otf");
        Button b1 = (Button) findViewById(R.id.button);
        b1.setTypeface(tf);
        Button b2 = (Button) findViewById(R.id.button2);
        b2.setTypeface(tf);
        Button b3 = (Button) findViewById(R.id.button3);
        b3.setTypeface(tf);
    }
    public void playMap(View v){
        Intent intent = new Intent(this, PlayMap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void browseMaps(View v){
        Intent intent = new Intent(this, BrowseMaps.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void createMap(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    // dEBUGGING high score
/*    public void testDone(View v){
        Intent intent = new Intent(this, DoneGame.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }*/


}
