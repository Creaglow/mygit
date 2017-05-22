package com.example.cpydr.cpys.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.entity.Article;
import com.example.cpydr.cpys.util.SuperClass;

public class WenActivity extends Activity {
    private SuperClass aClass;
    private ImageButton backButton;
    private TextView title;
    private WebView webView;
    private Article article;
    private Button fButton;
    private Button speak;
    private LinearLayout top;
    private RelativeLayout botton;
    private GestureDetector gestureScanner;// 手势
    private boolean isFullScreen = false; // 是否全屏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wen);
        aClass = new SuperClass();
        article = aClass.getArticle();
        backButton = (ImageButton)findViewById(R.id.article_wen_back);
        title = (TextView)findViewById(R.id.article_wen_title);
        webView = (WebView)findViewById(R.id.article_wen_webview);
        fButton = (Button)findViewById(R.id.wen_f);
        speak = (Button)findViewById(R.id.wen_speak);
        top = (LinearLayout)findViewById(R.id.webView_top);
        botton = (RelativeLayout)findViewById(R.id.webView_botton);

        title.setText(article.getShortTitle());
        //点击后返回
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WenActivity.this.finish();
            }
        });
        webView.loadUrl(article.getArcurl());
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webView.loadUrl(article.getArcurl());
            }
        });
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WenActivity.this,article.getId(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(WenActivity.this, PLActivity.class);
                startActivity(intent);
            }
        });



/*
        // 监听屏幕动作事件
        this.gestureScanner = new GestureDetector(this, (GestureDetector.OnGestureListener) this);
        this.gestureScanner.setIsLongpressEnabled(true);
        // 双击事件
        this.gestureScanner.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {

            // 用来判定该次点击是SingleTap而不是DoubleTap
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            // 通知DoubleTap手势中的事件，包含down、up和move事件
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }

            // 在双击的第二下，Touch down时触发
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (!isFullScreen) {
                    setFullScreen();
                } else {
                    quitFullScreen();
                }
                isFullScreen = !isFullScreen;

                return false;
            }
        });*/

    }
    /**
     * 全屏
     */
    private void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.top.setVisibility(View.GONE);
        this.botton.setVisibility(View.GONE);
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow()
                .clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        this.top.setVisibility(View.VISIBLE);
        this.botton.setVisibility(View.VISIBLE);
    }



}
