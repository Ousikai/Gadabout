package com.treasurehunt.dinobros.treasurehunt;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class database_sandbox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_sandbox);

        DatabaseHandler db = new DatabaseHandler(this);
        db.clearTable();
        //db.addMap(new TreasureMap("Silly Steps", "The Heart of Dark"));
        db.addMap(new TreasureMap("Silly Steps", "The Heart of Yak",
                "Klew0", "Crew2", "Fab3"));
        db.addMap(new TreasureMap("sECOND mAP", "cool descr",
                "1st", "2nd", "3rd"));
        TreasureMap foolsgold = db.getTreasureMap(2);
        foolsgold.set_map_name("better name");
        int fakefan = db.updateMap(foolsgold);
        db.addMap(new TreasureMap("chuck e. cheese", "don't care",
                "don't care", "don't care", "don't care"));
        TreasureMap noMore = db.getTreasureMap(1);
        db.deleteMap(noMore);
/*        //String thisStuff = " " +db.getNumMaps();
        TreasureMap booty = db.getTreasureMap(1);
        //TreasureMap booty = new TreasureMap(1, "Ravioli", "Mami Mia Pizzeria");
        long db_size = db.getNumMaps();

        String thisStuff = "db_size:" + db_size +
                " ,Id: "+booty.get_id()
                +"  ,Name: " + booty.get_map_name()
                + " ,Desc: " + booty.get_map_desc()
                + " ,Clue0:" +booty.get_clue0()
                + " ,Clue1:" +booty.get_clue2()
                + " ,Clue2:" +booty.get_clue1()
                ;*/

        ArrayList<TreasureMap> allMaps = db.getAllMaps();
        String thisStuff = "";
        for (TreasureMap booty : allMaps) {
            thisStuff += ("Id: " + booty.get_id() + ", Name:" + booty.get_map_name() + "\n");
        }
        thisStuff += ("->" +fakefan);
        TextView test_str = (TextView)findViewById(R.id.file_dir);
        test_str.setText(thisStuff);

        // Find the ListView resource.
        ListView mapsListView = (ListView) findViewById(R.id.listview_sandbox);
        ArrayAdapter<TreasureMap> adapter = new ArrayAdapter<TreasureMap>(this,
                android.R.layout.simple_list_item_1, allMaps);
        mapsListView.setAdapter(adapter);
    }
}