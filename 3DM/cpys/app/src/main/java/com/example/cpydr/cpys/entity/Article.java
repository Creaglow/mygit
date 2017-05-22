package com.example.cpydr.cpys.entity;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by cpydr on 2016/1/6.
 */
public class Article  {
    private String id;//ID
    private String typeid;
    private String shortTitle;//标题
    private String keyWords;//关键字
    private String arcurl;//文章地址
    private String litpic;//图片地址
    private String pubdate;//修改时间
    private String senddate;//发布时间
    private String writer;//作者
    private String feedback;//评论数
    private String click;//点击数


    //json解析
    public  void parseJSON(JSONObject json){
        try {
            this.id = json.getString("id");
            this.typeid = json.getString("typeid");
            this.shortTitle = json.getString("shorttitle");
            this.keyWords = json.getString("keywords");
            this.arcurl = json.getString("arcurl");
            this.litpic = json.getString("litpic");
            this.pubdate = json.getString("pubdate");
            this.senddate = json.getString("senddate");
            this.writer = json.getString("writer");
            this.feedback = json.getString("feedback");
            this.click = json.getString("click");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getArcurl() {
        return arcurl;
    }

    public void setArcurl(String arcurl) {
        this.arcurl = arcurl;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getSenddate() {
        return senddate;
    }

    public void setSenddate(String senddate) {
        this.senddate = senddate;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }
}
