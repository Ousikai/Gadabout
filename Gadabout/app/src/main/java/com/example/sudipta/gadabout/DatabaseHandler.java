package com.example.sudipta.gadabout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ousikai on 4/17/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "maps_database";
    // TreasureMap table name
    private static final String TABLE_TREASUREMAP = "maps_table";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MAP_NAME = "map_name";
    private static final String KEY_MAP_DESC = "map_desc";
    private static final String KEY_CLUE0 = "clue0";
    private static final String KEY_CLUE1 = "clue1";
    private static final String KEY_CLUE2 = "clue2";

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
                + KEY_CLUE0    + " TEXT,"
                + KEY_CLUE1 + " TEXT,"
                + KEY_CLUE2 + " TEXT"
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

    // Adding new TreasureMap
    public void addMap(TreasureMap treasureMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAP_NAME, treasureMap.get_map_name());
        values.put(KEY_MAP_DESC, treasureMap.get_map_desc());
        values.put(KEY_CLUE0, treasureMap.get_clue0());
        values.put(KEY_CLUE1, treasureMap.get_clue1());
        values.put(KEY_CLUE2, treasureMap.get_clue2());

        // Inserting Row
        db.insert(TABLE_TREASUREMAP, null, values);
        db.close(); // Closing database connection
    }

    // Getting single TreasureMap
    public TreasureMap getTreasureMap(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
/*        Cursor cursor = db.query(TABLE_TREASUREMAP, new String[] { KEY_ID,
                        KEY_MAP_NAME, KEY_MAP_DESC}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);*/

        Cursor cursor = db.query(TABLE_TREASUREMAP,null,KEY_ID + "=" + id,null,null,null,null);

        if (cursor != null){cursor.moveToFirst();}

        TreasureMap treasureMap = new TreasureMap(
                Integer.parseInt(cursor.getString(0)), // map ID
                cursor.getString(1), // map name
                cursor.getString(2), // map desc
                cursor.getString(3), //clue0
                cursor.getString(4), //clue1
                cursor.getString(5) //clue2
                );
        // return map
        return treasureMap;
    }

    // Getting All TreasureMaps
    public ArrayList<TreasureMap> getAllMaps() {
        ArrayList<TreasureMap> mapList = new ArrayList<TreasureMap>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TREASUREMAP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                mapList.add(new TreasureMap(
                        Integer.parseInt(cursor.getString(0)), // map ID
                        cursor.getString(1), // map name
                        cursor.getString(2), // map desc
                        cursor.getString(3), //clue0
                        cursor.getString(4), //clue1
                        cursor.getString(5) //clue2
                ));
            } while (cursor.moveToNext());
        }

        // return TreasureMap list
        return mapList;
    }

    // Updating single TreasureMap
    public int updateMap(TreasureMap treasureMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAP_NAME, treasureMap.get_map_name());
        values.put(KEY_MAP_DESC, treasureMap.get_map_desc());
        values.put(KEY_CLUE0, treasureMap.get_clue0());
        values.put(KEY_CLUE1, treasureMap.get_clue1());
        values.put(KEY_CLUE2, treasureMap.get_clue2());

        // updating row
        return db.update(TABLE_TREASUREMAP, values, KEY_ID + " = ?",
                new String[] { String.valueOf(treasureMap.get_id()) });
    }

    // Deleting single TreasureMap
    public void deleteMap(TreasureMap treasureMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TREASUREMAP, KEY_ID + " = ?",
                new String[]{String.valueOf(treasureMap.get_id())});
        db.close();
    }

    // Getting TreasureMap Count
    public long getNumMaps() {
        return DatabaseUtils.queryNumEntries(this.getReadableDatabase(), TABLE_TREASUREMAP);
    }
}
