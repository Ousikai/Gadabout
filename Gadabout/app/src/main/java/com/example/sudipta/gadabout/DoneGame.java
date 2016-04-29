package com.example.sudipta.gadabout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DoneGame extends AppCompatActivity {
    // Use shared preferences to update high score
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_game);

        // Set fancy font!
        Typeface tf = Typeface.createFromAsset(getAssets(), "exo.otf");
        TextView tv1 = (TextView) findViewById(R.id.textView4);
        tv1.setTypeface(tf);
        Button b1 = (Button) findViewById(R.id.next);
        b1.setTypeface(tf);

        // Update High Score
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        int high_score = preferenceSettings.getInt("high_score",0);
        System.out.println("Old High Score: "+ high_score);
        high_score += 10;
        preferenceEditor = preferenceSettings.edit();
        preferenceEditor.putInt("high_score", high_score);
        preferenceEditor.commit();
        int new_high_score = preferenceSettings.getInt("high_score",0);
        System.out.println("New High Score: " + new_high_score);

        tv1.setText("YOUR TOTAL: " + new_high_score + " Coins");
    }
    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
