package com.treasurehunt.dinobros.treasurehunt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ousikai on 4/17/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "maps_database";
    // TreasureMap table name
    private static final String TABLE_TREASUREMAP = "maps_table";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MAP_NAME = "map_name";
    private static final String KEY_MAP_DESC = "map_desc";
    private static final String KEY_CLUE0 = "clue0";
    //private static final String KEY_CLUE1 = "clue1";
    //private static final String KEY_CLUE2 = "clue2";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAPS_TABLE = "CREATE TABLE " + TABLE_TREASUREMAP + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_MAP_NAME + " TEXT,"
                + KEY_MAP_DESC + " TEXT,"
                + KEY_CLUE0    + " TEXT"
                //+ KEY_CLUE1 + " TEXT,"
                //+ KEY_CLUE2 + " TEXT,"
                + ")";
        db.execSQL(CREATE_MAPS_TABLE);
    }

    public void clearTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_TREASUREMAP, null, null);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TREASUREMAP);
        // Create tables again
        onCreate(db);
    }

    // Adding new map
    public void addMap(TreasureMap treasureMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAP_NAME, treasureMap.get_map_name());
        values.put(KEY_MAP_DESC, treasureMap.get_map_desc());
        values.put(KEY_CLUE0, treasureMap.get_clue0());
        //values.put(KEY_CLUE1, treasureMap.get_clue1());
        //values.put(KEY_CLUE2, treasureMap.get_clue2());

        // Inserting Row
        db.insert(TABLE_TREASUREMAP, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public TreasureMap getTreasureMap(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
/*        Cursor cursor = db.query(TABLE_TREASUREMAP, new String[] { KEY_ID,
                        KEY_MAP_NAME, KEY_MAP_DESC}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);*/

        Cursor cursor = db.query(TABLE_TREASUREMAP,null,KEY_ID + "=" + id,null,null,null,null);

        if (cursor != null){cursor.moveToFirst();}

        TreasureMap treasureMap = new TreasureMap(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return map
        return treasureMap;
    }

    // Getting contacts Count
    public long getNumMaps() {
        return DatabaseUtils.queryNumEntries(this.getReadableDatabase(), TABLE_TREASUREMAP);
    }
}
