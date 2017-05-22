package com.example.cpydr.cpys.util;

import android.app.Application;

import com.example.cpydr.cpys.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

/**
 * Created by cpydr on 2016/1/13.
 * 单链模式
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;
    //默认图片设置
    private BitmapDisplayConfig config;
    @Override
    public void onCreate() {

        super.onCreate();
        myApplication= this;
        config = new BitmapDisplayConfig();
        setConfig();
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    public BitmapDisplayConfig getConfig() {
        return config;
    }
    //提出来的方法
    public void setConfig() {
        config.setLoadingDrawable(getResources().getDrawable(R.drawable.product_default));
        config.setLoadFailedDrawable(getResources().getDrawable(R.drawable.product_default));
    }
}
