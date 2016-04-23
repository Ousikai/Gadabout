package com.example.sudipta.gadabout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class PlayMap extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<TreasureMap> adapter;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_map);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        lv = (ListView) findViewById(R.id.listView);

        DatabaseHandler db = new DatabaseHandler(this);
        db.clearTable();
        // Fill database with pre-loaded maps
        TreasureMap coolMap = new TreasureMap("Down Newcomb Road",
                "Cool clue desc!!",
                "Newcomb Road;38.033751;-78.508423",
                "Bridge;38.034872;-78.508171",
                "Bookstore Hill;38.035335;-78.507634");
        db.addMap(coolMap);
        db.addMap(new TreasureMap("Engineers Waverly",
                "Cool clue desc!!",
                "Newcomb Road;38.033751;-78.508423",
                "Bridge;38.034872;-78.508171",
                "Bookstore Hill;38.035335;-78.507634"));

        //Set up ArrayAdapter
        ArrayList<TreasureMap> allMaps = db.getAllMaps();
        adapter = new ArrayAdapter<TreasureMap>(this,android.R.layout.simple_list_item_1, allMaps);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                index = position;
                System.out.println(index);
            }
        });
/*       TreasureMap test1 = allMaps.get(0);
        ArrayList<String> testStr = test1.get_clue_desc();
        ArrayList<LatLng> testLatLng = test1.get_clue_latlng();
        for (int i=0;i<3;i++){
            System.out.print(testStr.get(i));
            System.out.println(testLatLng.get(i));
        }*/



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

}