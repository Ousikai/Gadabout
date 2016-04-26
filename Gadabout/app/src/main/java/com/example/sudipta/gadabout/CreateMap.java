package com.example.sudipta.gadabout;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class CreateMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_map);

        // Set fancy fonts!
        Typeface tf = Typeface.createFromAsset(getAssets(), "exo.otf");
        Button b1 = (Button) findViewById(R.id.save);
        b1.setTypeface(tf);
        Button b2 = (Button) findViewById(R.id.back);
        b2.setTypeface(tf);
        Button b3 = (Button) findViewById(R.id.play);
        b3.setTypeface(tf);
        EditText et= (EditText) findViewById(R.id.editText);
        et.setTypeface(tf);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public void back(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    public void saveMap(View v){
        Intent intent = new Intent(this, SaveMap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
