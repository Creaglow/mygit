package com.oracle.myblog.adapter;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oracle.myblog.R;
import com.oracle.myblog.cache.AsyncImageLoader;
import com.oracle.myblog.cache.AsyncImageLoader.ImageCallback;
import com.oracle.myblog.cache.ImageCacher;
import com.oracle.myblog.entity.Users;
import com.oracle.myblog.util.DateUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class UserListAdapter extends BaseAdapter {
	private PullToRefreshListView mListView;
	private AsyncImageLoader mAsyncImageLoader;
	private List<Users> userslist;
	private LayoutInflater mInflater;
	private Context mContext;

	public UserListAdapter(Context context, PullToRefreshListView listView,
			List<Users> usersList) {
		this.mContext = context;
		this.mListView =  listView;
		this.userslist = usersList;
		this.mAsyncImageLoader = new AsyncImageLoader(mContext);
		this.mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return userslist.size();
	}

	public Object getItem(int position) {
		return userslist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View v, ViewGroup group) {
		ViewHolder viewHolder = null;
		
		Users entity = userslist.get(position);
		if (v != null && R.id.pull_list == v.getId()) {
			viewHolder = (ViewHolder) v.getTag();
		} else {
			viewHolder = new ViewHolder();
			v = mInflater.inflate(R.layout.search_list_item, null);

			viewHolder.author_list_title = (TextView) v
					.findViewById(R.id.author_list_title);
			viewHolder.author_list_url = (TextView) v
					.findViewById(R.id.author_list_url);
			viewHolder.author_list_avatar = (ImageView) v
					.findViewById(R.id.author_list_avatar);
			viewHolder.author_list_username = (TextView) v
					.findViewById(R.id.author_list_username);
			viewHolder.author_list_blogcount = (TextView) v
					.findViewById(R.id.author_list_blogcount);
			viewHolder.author_list_update = (TextView) v
					.findViewById(R.id.author_list_update);
		}
		String tag = entity.GetAvator();
		if (tag.contains("?")) {// 截断?后的字符串，避免无效图片
			tag = tag.substring(0, tag.indexOf("?"));
		}

		viewHolder.author_list_avatar.setTag(tag);
		Drawable cachedImage = mAsyncImageLoader.loadDrawable(
				ImageCacher.EnumImageType.Avatar, tag, new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable, String tag) {
						Log.i("Drawable", tag);
						ImageView imageViewByTag = (ImageView) mListView
								.findViewWithTag(tag);
						if (imageViewByTag != null && imageDrawable != null) {
							imageViewByTag.setImageDrawable(imageDrawable);
						}
					}
				});
		// 

		viewHolder.author_list_title.setText(entity.GetBlogName());
		viewHolder.author_list_url.setText(entity.GetBlogUrl());
		viewHolder.author_list_username.setText(String.valueOf(entity
				.GetUserName()));
		// ʱ��
		String simpleDateString = DateUtil.DateToChineseString(entity
				.GetLastUpdate());//
		viewHolder.author_list_update.setText(simpleDateString);
		viewHolder.author_list_blogcount.setText(String.valueOf(entity
				.GetBlogCount()));

		v.setTag(viewHolder);
		return v;
	}
	/**
	 * 得到数据
	 * 
	 * @return
	 */
	public List<Users> GetData() {
		return userslist;
	}
	/**
	 * 插入
	 * 
	 * @param list
	 */
	public void InsertData(List<Users> list) {
		this.userslist.addAll(0, list);
		this.notifyDataSetChanged();
	}
	/**
	 * 往后面增加数据
	 * 
	 * @param listBlog
	 */
	public void AddMoreData(List<Users> list) {
		this.userslist.addAll(list);
		this.notifyDataSetChanged();
	}
	/**
	 * 要显示的控件类
	 * 
	 * @author Administrator
	 * 
	 */
	public class ViewHolder {
		TextView author_list_title;
		TextView author_list_url;
		ImageView author_list_avatar;
		TextView author_list_username;
		TextView author_list_blogcount;
		TextView author_list_update;
	}
}
