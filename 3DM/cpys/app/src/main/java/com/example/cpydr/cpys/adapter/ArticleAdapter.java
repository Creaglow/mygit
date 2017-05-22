package com.example.cpydr.cpys.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.entity.Article;
import com.example.cpydr.cpys.util.MyApplication;
import com.lidroid.xutils.BitmapUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cpydr on 2016/1/6
 */
public class ArticleAdapter extends BaseAdapter {
    private final static String PATH="http://www.3dmgame.com";
    private Context context;
    private List<Article> list;
    private BitmapUtils bitmapUtils;


    public ArticleAdapter(Context context, List<Article> list) {
        super();
        this.context = context;
        this.list = list;

        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position){

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        Article article =list.get(position);
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.article_listview, parent, false);
            holder.img = (ImageView)convertView.findViewById(R.id.article_title_img);
            holder.num = (TextView)convertView.findViewById(R.id.article_num);
            holder.time = (TextView) convertView.findViewById(R.id.article_time);
            holder.title = (TextView) convertView.findViewById(R.id.article_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.title.setText(article.getShortTitle());
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Long time =1000*(Long.parseLong(article.getSenddate()));
        holder.time.setText(sdf.format(time));
        holder.num.setText(article.getClick());

        bitmapUtils.display(holder.img, PATH + article.getLitpic(), MyApplication.getMyApplication().getConfig());



//        holder.num.setText(article.getNum());
//        holder.img.setImageResource(article.getImg());//资源id
        return convertView;
    }

    class ViewHolder {
        ImageView img;//头像控件
        TextView title;//文章标题
        TextView time;//发布时间
        TextView num;//评论数
    }
}
