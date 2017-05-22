package com.example.cpydr.cpys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cpydr.cpys.R;
import com.example.cpydr.cpys.entity.Comment;

import java.util.List;

/**
 * Created by cpydr on 2016/1/13.
 */
public class ComentAdapter extends BaseAdapter {

    private List<Comment> comments;
    private Context context;

    public ComentAdapter(List<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        Comment comment= (Comment)getItem(position);
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.pl_one_itme,parent,false);
            holder.content = (TextView)convertView.findViewById(R.id.PL_content);
            holder.user = (TextView)convertView.findViewById(R.id.PL_user);
            holder.num = (TextView)convertView.findViewById(R.id.PL_lou);
            holder.img = (ImageView)convertView.findViewById(R.id.PL_host_img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.content.setText(comment.getMsg());
        holder.user.setText(comment.getUsername());
        holder.num.setText("第"+comment.getFloor()+"楼");


        return convertView;
    }
    class ViewHolder {
        ImageView img;//头像控件
        TextView user;//文章标题
        TextView num;//几楼
        TextView content;//评论内容
    }
}
