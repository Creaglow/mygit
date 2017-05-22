package com.example.cpydr.cpys.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.entity.Game;
import com.example.cpydr.cpys.util.MyApplication;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by cpydr on 2016/1/12.
 */
public class GameAdapter extends BaseAdapter {
    private Context context;
    private List<Game> games;
    private BitmapUtils bitmapUtils;
    private final static String PATH="http://www.3dmgame.com";

    public GameAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int position) {
        return games.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Game game = games.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView =LayoutInflater.from(context).inflate(R.layout.item_game_layout,parent,false);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.game_img);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.game_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Log.i("cpy>>>>>>>>>>>>>>>>>>",games.size()+"");
        bitmapUtils.display(viewHolder.imageView,PATH+game.getLitpic(), MyApplication.getMyApplication().getConfig());
        viewHolder.textView.setText(game.getTitle());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
