package com.example.cpydr.cpys.util;

import com.example.cpydr.cpys.entity.Article;
import com.example.cpydr.cpys.entity.Game;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cpydr on 2016/1/12.
 */
public class SuperClass {
    private static Article article;//用于数据传输
    private static Game game;
    private static String gamePath;
    private static HttpUtils httpUtils;
    public static String getGamePath(int type) {
        return "http://www.3dmgame.com/sitemap/api.php?row=30&typeid="+type+"&paging=1&page=";
    }

    //用于数据传输
    public  Article getArticle() {
        return article;
    }
    public void setArticle(Article article) {
        SuperClass.article = article;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        SuperClass.game = game;
    }

    public static HttpUtils getHttpUtils() {
        return httpUtils;
    }
//mars
    public static void setHttpUtils(HttpUtils httpUtils) {
        SuperClass.httpUtils = httpUtils;
    }
    public String getDateFromString(String time){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Long l =1000*(Long.parseLong(time));
        return  sdf.format(l);
    }

}
