package com.example.sudipta.gadabout;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ousikai on 4/17/16.
 */
public class TreasureMap {
    private int _id;
    private String _map_name;
    private String _map_desc;
    private String _clue0;
    private String _clue1;
    private String _clue2;


    // Empty Constructor
    public TreasureMap() {
    }

    // constructor
    public TreasureMap(int id, String map_name, String map_desc,
                       String clue0,
                       String clue1,
                       String clue2
    ){
        this._id = id;
        this._map_name = map_name;
        this._map_desc = map_desc;
        this._clue0 = clue0;
        this._clue1 = clue1;
        this._clue2 = clue2;
    }

    // constructor
    public TreasureMap(String map_name, String map_desc,
                       String clue0,
                       String clue1,
                       String clue2
    ){
        this._map_name = map_name;
        this._map_desc = map_desc;
        this._clue0 = clue0;
        this._clue1 = clue1;
        this._clue2 = clue2;
    }

    @Override
    public String toString() {
        return this.get_map_name();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_map_name() {
        return _map_name;
    }

    public void set_map_name(String _map_name) {
        this._map_name = _map_name;
    }

    public String get_map_desc() {
        return _map_desc;
    }

    public void set_map_desc(String _map_desc) {
        this._map_desc = _map_desc;
    }

    public String get_clue0() {
        return _clue0;
    }

    public void set_clue0(String _clue0) {
        this._clue0 = _clue0;
    }

    public String get_clue1() {
        return _clue1;
    }

    public void set_clue1(String _clue1) {
        this._clue1 = _clue1;
    }

    public String get_clue2() {
        return _clue2;
    }

    public void set_clue2(String _clue2) {
        this._clue2 = _clue2;
    }

    public ArrayList<String> get_clue_desc(){
        ArrayList<String> clue_desc = new ArrayList<String>();
        clue_desc.add(this._clue0.split(";")[0]);
        clue_desc.add(this._clue1.split(";")[0]);
        clue_desc.add(this._clue2.split(";")[0]);
        return clue_desc;
    }

    public ArrayList<LatLng> get_clue_latlng(){
        ArrayList<LatLng> clue_latng = new ArrayList<LatLng>();
        clue_latng.add(new LatLng(Double.parseDouble(this._clue0.split(";")[1]),Double.parseDouble(this._clue0.split(";")[2])));
        clue_latng.add(new LatLng(Double.parseDouble(this._clue1.split(";")[1]),Double.parseDouble(this._clue1.split(";")[2])));
        clue_latng.add(new LatLng(Double.parseDouble(this._clue2.split(";")[1]),Double.parseDouble(this._clue2.split(";")[2])));
        return clue_latng;
    }

}
