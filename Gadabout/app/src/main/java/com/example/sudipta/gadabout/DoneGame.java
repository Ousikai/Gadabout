package com.example.sudipta.gadabout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DoneGame extends AppCompatActivity {
    // Use shared preferences to update high score
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_game);

        // Update High Score
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        int high_score = preferenceSettings.getInt("high_score",0);
        System.out.println("Old High Score: "+ high_score);
        high_score += 10;
        preferenceEditor = preferenceSettings.edit();
        preferenceEditor.putInt("high_score", high_score);
        preferenceEditor.commit();
        int test_high_score = preferenceSettings.getInt("high_score",0);
        System.out.println("New High Score: " + test_high_score);

    }
    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
