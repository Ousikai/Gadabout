package com.example.sudipta.gadabout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity {
    int curr_map_index;
    TreasureMap curr_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        curr_map_index = getIntent().getIntExtra("index",-1);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<TreasureMap> allMaps = db.getAllMaps();
        curr_map = allMaps.get(curr_map_index);

    }
    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void checkHere(View v){
        TextView tv = (TextView)findViewById(R.id.textView2);
        tv.setText("YOU'RE RIGHT!");
        TextView tv2 = (TextView)findViewById(R.id.textView3);
        tv2.setText("Getting colder!");
        Button b1 = (Button) findViewById(R.id.next);
        b1.setEnabled(true);
        //System.out.println(curr_map.get_map_name());
        //System.out.println(curr_map.get_map_desc());
    }
    public void nextClue(View v){
        Intent intent = new Intent(this, DoneGame.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
