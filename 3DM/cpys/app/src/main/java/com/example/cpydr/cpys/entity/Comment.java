package com.example.cpydr.cpys.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cpydr on 2016/1/13.
 */
public class Comment {
    private String id;
    private String username;
    private String msg;
    private String floor;

    public void parseJSON(JSONObject object){
        try {
            this.id = object.getString("id");
            this.username = object.getString("username");
            this.msg = object.getString("msg");
            this.floor = object.getString("floor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
