package com.example.sudipta.gadabout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
/*        db.clearTable();
        // Fill database with pre-loaded maps
        TreasureMap coolMap = new TreasureMap("Down Newcomb Road",
                "Cool clue desc!!",
                "Newcomb Road;38.033751;-78.508423",
                "Bridge;38.034872;-78.508171",
                "Bookstore Hill;38.035335;-78.507634");
        db.addMap(coolMap);*/
//        db.addMap(new TreasureMap("Engineers Waverly",
//                "Cool clue desc!!",
//                "Newcomb Road;38.033751;-78.508423",
//                "Bridge;38.034872;-78.508171",
//                "Bookstore Hill;38.035335;-78.507634"));

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
    }
    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void playGame(View v){
        Intent intent = new Intent(this, GamePlay.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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

}