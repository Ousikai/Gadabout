package com.example.sudipta.gadabout;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class BrowseMaps extends AppCompatActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_browse_maps);
        lv = (ListView) findViewById(R.id.listView);

        // Fancy fonts
        Typeface tf = Typeface.createFromAsset(getAssets(), "exo.otf");
        Button b1 = (Button) findViewById(R.id.button4);
        b1.setTypeface(tf);
        Button b2 = (Button) findViewById(R.id.back);
        b2.setTypeface(tf);
        Button b3 = (Button) findViewById(R.id.play);
        b3.setTypeface(tf);
        EditText et= (EditText) findViewById(R.id.editText);
        et.setTypeface(tf);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        ArrayList<String> browseList = new ArrayList<String>();
        browseList.add("A Map that I Made");
        browseList.add("UVA Treasure Hunt");
        browseList.add("Red Rackham’s Treasure");
        browseList.add("The Treasure Within");
        browseList.add("This Map is Mine");
        browseList.add("The Best Map");
        browseList.add("Blackbeard’s Gold");
        browseList.add("Patchy the Pirate’s Map");
        browseList.add("Gold, Gold and More Gold");
        browseList.add("The Only Treasure Map You’ll Need");
        browseList.add("The Other Map");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, browseList);

        lv.setAdapter(arrayAdapter);
    }
    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void getMap(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
