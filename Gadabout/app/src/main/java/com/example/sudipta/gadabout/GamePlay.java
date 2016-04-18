package com.example.sudipta.gadabout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GamePlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
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
    }
    public void nextClue(View v){
        Intent intent = new Intent(this, DoneGame.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
