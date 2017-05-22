package com.example.cpydr.cpys.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.entity.Article;
import com.example.cpydr.cpys.fragment.ArticleFragment;
import com.example.cpydr.cpys.fragment.ForumFragment;
import com.example.cpydr.cpys.fragment.GameFragment;

import java.util.List;


public class MainActivity extends FragmentActivity {
    private List<Article> list;
    private RadioGroup radioGroup ;
    //用于管理fragment的操作
    private FragmentManager fragmentManager;
    //设置fragment操作事物
    private FragmentTransaction fragmentTransaction;
    private ImageButton button;
    private TextView textView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.top_tag);
        fragmentManager = getSupportFragmentManager();
        change(new ArticleFragment());
        radioGroup = (RadioGroup)findViewById(R.id.main_radioGroup);
        button = (ImageButton)findViewById(R.id.loading_img);
        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                button.setVisibility(View.GONE);
            }
        };
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Message message = new Message();
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_rad1:
                        change(new ArticleFragment());
                        textView.setText("文章");
                        break;
                    case R.id.main_rad2:
                        change(new ForumFragment());
                        textView.setText("论坛");
                        break;
                    case R.id.main_rad3:
                        change(new GameFragment());
                        textView.setText("游戏");
                        break;
                }
            }
        });

    }
    public void change(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();//开始事件
        fragmentTransaction.replace(R.id.content, fragment);//代替
        fragmentTransaction.commit();//提交
    }

}
