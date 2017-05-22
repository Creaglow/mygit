package com.example.cpydr.cpys.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cpydr on 2016/1/12.
 */
public class Game {
    private String id;
    private String typeid;
    private String title;
    private String shorttitle;
    private String litpic;
    private String keywords;
    private String senddate;
    private String terrace;
    private String tid;
    private String made_company;//开发商
    private String release_company;//发行厂商
    private String description;
    private String transname;

    public String getTransname() {
        return transname;
    }

    public void setTransname(String transname) {
        this.transname = transname;
    }

    public void parase(JSONObject json){
        try {
            this.id = json.getString("id");
            this.typeid = json.getString("typeid");
            this.title = json.getString("title");
            this.shorttitle = json.getString("shorttitle");
            this.litpic = json.getString("litpic");
            this.keywords = json.getString("keywords");
            this.senddate = json.getString("senddate");
            this.terrace = json.getString("terrace");
            this.tid = json.getString("tid");
            this.description = json.getString("description");
            this.made_company = json.getString("made_company");
            this.release_company = json.getString("release_company");
            this.transname = json.getString("game_trans_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getMade_company() {
        return made_company;
    }

    public void setMade_company(String made_company) {
        this.made_company = made_company;
    }

    public String getRelease_company() {
        return release_company;
    }

    public void setRelease_company(String release_company) {
        this.release_company = release_company;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShorttitle() {
        return shorttitle;
    }

    public void setShorttitle(String shorttitle) {
        this.shorttitle = shorttitle;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTerrace() {
        return terrace;
    }

    public void setTerrace(String terrace) {
        this.terrace = terrace;
    }
}
