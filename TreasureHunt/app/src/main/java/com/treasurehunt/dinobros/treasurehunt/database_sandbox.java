package com.treasurehunt.dinobros.treasurehunt;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class database_sandbox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_sandbox);

        DatabaseHandler db = new DatabaseHandler(this);
        db.clearTable();
        //db.addMap(new TreasureMap("Silly Steps", "The Heart of Dark"));
        db.addMap(new TreasureMap("Silly Steps", "The Heart of Yak", "Klew0"));
        //String thisStuff = " " +db.getNumMaps();
        TreasureMap booty = db.getTreasureMap(1);
        //TreasureMap booty = new TreasureMap(1, "Ravioli", "Mami Mia Pizzeria");
        long db_size = db.getNumMaps();

        String thisStuff = "db_size:" + db_size +
                " ,Id: "+booty.get_id()
                +"  ,Name: " + booty.get_map_name()
                + " ,Desc: " + booty.get_map_desc()
                + " ,Clue0:" +booty.get_clue0()
                ;

        TextView test_str = (TextView)findViewById(R.id.file_dir);
        test_str.setText(thisStuff);
    }
}