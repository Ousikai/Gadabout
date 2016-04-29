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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PlayMap extends AppCompatActivity {
    ListView lv;
    ArrayList<TreasureMap> allMaps;
    ArrayAdapter<TreasureMap> adapter;
    int index;
    DatabaseHandler db;
    TreasureMap selectedMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_map);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        lv = (ListView) findViewById(R.id.listView);

        db = new DatabaseHandler(this);
        //Set up ArrayAdapter
        allMaps = db.getAllMaps();
        adapter = new ArrayAdapter<TreasureMap>(this,android.R.layout.simple_list_item_1, allMaps);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                index = position;
                System.out.println(index);
                selectedMap = allMaps.get(index);

            }
        });

        // Set fancy fonts!
        Typeface tf = Typeface.createFromAsset(getAssets(), "exo.otf");
        Button b1 = (Button) findViewById(R.id.delete);
        b1.setTypeface(tf);
        Button b2 = (Button) findViewById(R.id.play);
        b2.setTypeface(tf);
        Button b3 = (Button) findViewById(R.id.back);
        b3.setTypeface(tf);
        TextView score = (TextView) findViewById(R.id.score);
        score.setTypeface(tf);

        score.setText("Earn Gadabout coins with every map you finish!");

        // Resume game if started
        resumeGame();
    }
    public void back(View v){
/*       if (selectedMap!=null) {
            String allSites = "";
            allSites+=selectedMap.get_clue0()+"\n";
            allSites+=selectedMap.get_clue1()+"\n";
            allSites+=selectedMap.get_clue2()+"\n";
            allSites+=selectedMap.get_clue3()+"\n";
            allSites+=selectedMap.get_clue4()+"\n";
            Toast.makeText(this, allSites, Toast.LENGTH_SHORT).show();
        }*/

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void playGame(View v){
        Intent intent = new Intent(this, GamePlay.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    public void delete(View v) {
        if (selectedMap!=null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Map");
            builder.setMessage("Do you give up on this finding these treasures? " +
                    "There may never be a chance to find it again!");

            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println("Picked no");
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println("Picked YES");
                    //DatabaseHandler db = new DatabaseHandler(this);
                    //TreasureMap noMore = allMaps.get(index);
                    db.deleteMap(selectedMap);
                    recreate();
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    public void resumeGame(){
        SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if (settings.getBoolean("saved", false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Resume Game?");
            builder.setMessage("Hello traveler! Would like to pick up where you left off on your last quest?");

            builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    int i = settings.getInt("saved_map", -1);
                    int c = settings.getInt("saved_clue", 0);
                    editor.putInt("saved_map", -1);
                    editor.putInt("saved_clue", 0);
                    editor.putBoolean("saved", false);
                    editor.commit();
                    Intent intent = new Intent(PlayMap.this, GamePlay.class);
                    intent.putExtra("saved_index", i);
                    intent.putExtra("saved_clue", c);
                    intent.putExtra("saved", true);
                    startActivity(intent);
                    dialog.dismiss();
                }

            });

            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
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