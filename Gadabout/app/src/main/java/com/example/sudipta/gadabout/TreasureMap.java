package com.example.sudipta.gadabout;

import com.google.android.gms.maps.model.LatLng;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ousikai on 4/17/16.
 */
public class TreasureMap implements Serializable{
    private int _id;
    private String _map_name;
    private String _map_desc;
    private String _clue0;
    private String _clue1;
    private String _clue2;
    private String _clue3;
    private String _clue4;


    // Empty Constructor
    public TreasureMap() {
    }

    // constructor
    public TreasureMap(int id,
                       String map_name,
                       String map_desc,
                       String clue0,
                       String clue1,
                       String clue2,
                       String clue3,
                       String clue4
    ){
        this._id = id;
        this._map_name = map_name;
        this._map_desc = map_desc;
        this._clue0 = clue0;
        this._clue1 = clue1;
        this._clue2 = clue2;
        this._clue3 = clue3;
        this._clue4 = clue4;
    }

    // constructor
    public TreasureMap(String map_name,
                       String map_desc,
                       String clue0,
                       String clue1,
                       String clue2,
                       String clue3,
                       String clue4
    ){
        this._map_name = map_name;
        this._map_desc = map_desc;
        this._clue0 = clue0;
        this._clue1 = clue1;
        this._clue2 = clue2;
        this._clue3 = clue3;
        this._clue4 = clue4;
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

    public String get_clue3() {
        return _clue3;
    }

    public void set_clue3(String _clue3) {
        this._clue3 = _clue3;
    }

    public String get_clue4() {
        return _clue4;
    }

    public void set_clue4(String _clue4) {
        this._clue4 = _clue4;
    }

    public ArrayList<String> get_all_clue(){
        ArrayList<String> clue_desc = new ArrayList<String>();
        clue_desc.add(this._clue0.split(";")[0]);
        clue_desc.add(this._clue1.split(";")[0]);
        clue_desc.add(this._clue2.split(";")[0]);
        clue_desc.add(this._clue3.split(";")[0]);
        clue_desc.add(this._clue4.split(";")[0]);
        return clue_desc;
    }

    public ArrayList<String> get_clue_desc(){
        ArrayList<String> clue_desc = new ArrayList<String>();
        if (!this._clue0.equals("not set")){
            clue_desc.add(this._clue0.split(";")[0]);
        }
        if (!this._clue1.equals("not set")) {
            clue_desc.add(this._clue1.split(";")[0]);
        }
        if (!this._clue2.equals("not set")){
            clue_desc.add(this._clue2.split(";")[0]);
        }
        if (!this._clue3.equals("not set")){
            clue_desc.add(this._clue3.split(";")[0]);
        }
        if (!this._clue4.equals("not set")){
            clue_desc.add(this._clue4.split(";")[0]);
        }
        return clue_desc;
    }

    public ArrayList<LatLng> get_clue_latlng(){
        ArrayList<LatLng> clue_latng = new ArrayList<LatLng>();
        if (!this._clue0.equals("not set")){
            clue_latng.add(new LatLng(Double.parseDouble(this._clue0.split(";")[1]),
                    Double.parseDouble(this._clue0.split(";")[2])));
        }
        if (!this._clue1.equals("not set")) {
            clue_latng.add(new LatLng(Double.parseDouble(this._clue1.split(";")[1]),
                    Double.parseDouble(this._clue1.split(";")[2])));
        }
        if (!this._clue2.equals("not set")){
            clue_latng.add(new LatLng(Double.parseDouble(this._clue2.split(";")[1]),
                    Double.parseDouble(this._clue2.split(";")[2])));
        }
        if (!this._clue3.equals("not set")){
            clue_latng.add(new LatLng(Double.parseDouble(this._clue3.split(";")[1]),
                    Double.parseDouble(this._clue3.split(";")[2])));
        }
        if (!this._clue4.equals("not set")){
            clue_latng.add(new LatLng(Double.parseDouble(this._clue4.split(";")[1]),
                    Double.parseDouble(this._clue4.split(";")[2])));
        }

        return clue_latng;
    }

    public void setAnyClue(int clueNum, String clueStr){
        if (clueNum==0) {this.set_clue0(clueStr);}
        else if (clueNum==1){this.set_clue1(clueStr);}
        else if (clueNum==2){this.set_clue2(clueStr);}
        else if (clueNum==3){this.set_clue3(clueStr);}
        else if (clueNum==4){this.set_clue4(clueStr);}
    }

}
