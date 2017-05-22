package com.example.cpydr.cpys.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.entity.Game;
import com.example.cpydr.cpys.util.MyApplication;
import com.example.cpydr.cpys.util.SuperClass;
import com.lidroid.xutils.BitmapUtils;

public class TowActivity extends Activity {
    private ImageView imageView;
    private TextView name;
    private TextView type;
    private TextView kaifa;
    private TextView time;
    private TextView product;
    private TextView net;
    private TextView place,content;
    private SuperClass aClass;
    private Game game;
    private BitmapUtils bitmapUtils;
    private ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tow);
        imageView = (ImageView)findViewById(R.id.game_imageView);
        name = (TextView)findViewById(R.id.game_name);
        type = (TextView)findViewById(R.id.game_type);
        kaifa = (TextView)findViewById(R.id.kaifa);
        content= (TextView)findViewById(R.id.game_TextView);
        time = (TextView)findViewById(R.id.game_time);
        product = (TextView)findViewById(R.id.game_product);
        net = (TextView)findViewById(R.id.game_net);
        place = (TextView)findViewById(R.id.game_place);
        imageButton = (ImageButton)findViewById(R.id.game_tow_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TowActivity.this.finish();
            }
        });
        aClass = new SuperClass();
        game = aClass.getGame();
        bitmapUtils = new BitmapUtils(TowActivity.this);
        bitmapUtils.display(imageView, "http://www.3dmgame.com" + game.getLitpic(), MyApplication.getMyApplication().getConfig());
        name.setText(game.getTitle());
        type.setText(game.getTid());
        kaifa.setText(game.getMade_company());
        time.setText(aClass.getDateFromString(game.getSenddate()));
        product.setText(game.getRelease_company());
        net.setText("点击进入");
        place.setText(game.getTerrace());
        content.setText(game.getDescription());




    }
}
