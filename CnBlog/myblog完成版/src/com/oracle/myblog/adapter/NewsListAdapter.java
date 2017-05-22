package com.oracle.myblog.adapter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oracle.myblog.R;
import com.oracle.myblog.cache.AsyncImageLoader;
import com.oracle.myblog.entity.News;
import com.oracle.myblog.util.DateUtil;


public class NewsListAdapter extends BaseAdapter {

	private PullToRefreshListView mListView;
	private AsyncImageLoader mAsyncImageLoader;
	private List<News> mNewsList;
	private LayoutInflater minflater;
	private Context mContext;

	public NewsListAdapter(Context context, PullToRefreshListView listView,
			List<News> newsList) {
		super();
		this.mListView = listView;
		this.mNewsList = newsList;
		this.mContext = context;
		this.mAsyncImageLoader = new AsyncImageLoader(mContext);
		this.minflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return this.mNewsList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mNewsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup group) {
		ViewHolder viewHolder = null;

		News entity = this.mNewsList.get(position);

		if (v != null && R.id.pull_list == v.getId()) {
			viewHolder = (ViewHolder) v.getTag();
		} else {
			viewHolder = new ViewHolder();
			v = minflater.inflate(R.layout.activity_news_list_item, null);

			viewHolder.text_title = (TextView) v
					.findViewById(R.id.recommend_text_title1);
			viewHolder.text_desc = (TextView) v
					.findViewById(R.id.recommend_text_desc1);

			viewHolder.text_comments = (TextView) v
					.findViewById(R.id.recommend_text_comments1);
			viewHolder.text_view = (TextView) v
					.findViewById(R.id.recommend_text_view1);

			viewHolder.text_formatdate = (TextView) v//..........................................................
					.findViewById(R.id.recommend_text_formatdate1);

			viewHolder.icon_downloaded = (ImageView) v
					.findViewById(R.id.icon_downloaded);
		} 
		 

		// 标题
		viewHolder.text_title.setText(entity.getNewsTitle());
		viewHolder.text_desc.setText(entity.getSummary());
		//viewHolder.text_author.setText(entity.getAuthor());
		//viewHolder.text_author.setText("");
		viewHolder.text_comments
				.setText(String.valueOf(entity.getCommentNum()));
		viewHolder.text_view.setText(String.valueOf(entity.getViewNum()));
		
		viewHolder.text_formatdate.setText(DateUtil.DateToChineseString(entity
				.getUpdateTime()));

		 

		v.setTag(viewHolder);

		return v;
	}

	/**
	 * 要显示的控件类
	 * 
	 * @author Administrator
	 * 
	 */
	public class ViewHolder {
		TextView text_title;
		TextView text_desc;
		ImageView imageIcon;
		TextView text_view;
		TextView text_comments;
		TextView text_author;
		TextView text_formatdate;
		TextView text_news_id;
		ImageView icon_downloaded;
	}

	/**
	 * 得到博客列表的数据
	 * 
	 * @return
	 */
	public List<News> getData() {
		return mNewsList;
	}

	/**
	 * 插入数据到最前面
	 * 
	 * @param listNews
	 */
	public void insertData(List<News> listNews) {
		this.mNewsList.addAll(0, listNews);
		this.notifyDataSetChanged();
	}

	/**
	 * 往后面增加数据
	 * 
	 * @param listNews
	 */
	public void addData(List<News> listNews) {
		this.mNewsList.addAll(listNews);
		this.notifyDataSetChanged();
	}

	/**
	 * 移除数据
	 * 
	 * @param entity
	 */
	public void removeData(News entity) {
		int len = this.mNewsList.size();
		for (int i = 0; i < len; i++) {
			if (this.mNewsList.get(i).getNewsId() == entity.getNewsId()) {
				this.mNewsList.remove(i);
				this.notifyDataSetChanged();
				break;
			}
		}
	}

}
