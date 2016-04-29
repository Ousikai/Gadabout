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

        resumeGame();
    }
    public void playMap(View v){
        Intent intent = new Intent(this, PlayMap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void browseMaps(View v){
        Intent intent = new Intent(this, BrowseMaps.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void createMap(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void resumeGame(){
        SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if (settings.getBoolean("saved", false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Resume Game?");
            builder.setMessage("Hello traveler! Would like to pick up where you left off on your last quest?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    int i = settings.getInt("saved_map", -1);
                    int c = settings.getInt("saved_clue", 0);
                    editor.putInt("saved_map", -1);
                    editor.putInt("saved_clue", 0);
                    editor.putBoolean("saved", false);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, GamePlay.class);
                    intent.putExtra("saved_index", i);
                    intent.putExtra("saved_clue", c);
                    intent.putExtra("saved", true);
                    startActivity(intent);
                    dialog.dismiss();
                }

            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("saved_map", -1);
                    editor.putInt("saved_clue", 0);
                    editor.putBoolean("saved", false);
                    editor.commit();
                    System.out.println("Main act in saved: " + settings.getBoolean("saved", true));
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
