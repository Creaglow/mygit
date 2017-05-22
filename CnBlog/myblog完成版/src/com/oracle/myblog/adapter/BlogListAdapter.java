package com.oracle.myblog.adapter;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oracle.myblog.R;
import com.oracle.myblog.cache.AsyncImageLoader;
import com.oracle.myblog.cache.AsyncImageLoader.ImageCallback;
import com.oracle.myblog.cache.ImageCacher;
import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.util.DateUtil;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 博客列表数据匹配
 * 
 * @author Administrator
 * 
 */
public class BlogListAdapter extends BaseAdapter {

	private PullToRefreshListView mListView;
	private AsyncImageLoader mAsyncImageLoader;
	private List<Blog> mBlogList;
	private LayoutInflater minflater;
	private Context mContext;

	public BlogListAdapter(Context context, PullToRefreshListView listView,
			List<Blog> blogList) {
		super();
		this.mListView = listView;
		this.mBlogList = blogList;
		this.mContext = context;
		this.mAsyncImageLoader = new AsyncImageLoader(mContext);
		this.minflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return this.mBlogList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mBlogList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup group) {
		ViewHolder viewHolder = null;

		Blog entity = this.mBlogList.get(position);

		if (v != null && R.id.pull_list == v.getId()) {
			viewHolder = (ViewHolder) v.getTag();
		} else {
			viewHolder = new ViewHolder();
			v = minflater.inflate(R.layout.layout_blog_list_item, null);

			viewHolder.text_title = (TextView) v
					.findViewById(R.id.recommend_text_title);
			viewHolder.text_desc = (TextView) v
					.findViewById(R.id.recommend_text_desc);
			viewHolder.imageIcon = (ImageView) v
					.findViewById(R.id.recommend_image_icon);			

			viewHolder.text_comments = (TextView) v
					.findViewById(R.id.recommend_text_comments);
			viewHolder.text_view = (TextView) v
					.findViewById(R.id.recommend_text_view);

			viewHolder.text_formatdate = (TextView) v
					.findViewById(R.id.recommend_text_formatdate);

			viewHolder.icon_downloaded = (ImageView) v
					.findViewById(R.id.icon_downloaded);
		}

		// 加载头像
		if (entity.getAvator() != null) {
			String tag = entity.getAvator();
			// 截断?后的字符串，避免无效图片
			if (tag.contains("?")) {
				tag = tag.substring(0, tag.indexOf("?"));
			}
			Log.d("BlogActivity", "头像:" + tag);
			// 设定图标自定义属性
			viewHolder.imageIcon.setTag(tag);
			// 加载图片
			Drawable cachedImage = mAsyncImageLoader.loadDrawable(
					ImageCacher.EnumImageType.Avatar, tag, new ImageCallback() {
						@Override
						public void imageLoaded(Drawable imageDrawable,
								String tag) {
							ImageView imgByTag = (ImageView) mListView
									.findViewWithTag(tag);

							if (imgByTag != null && imageDrawable != null) {
								imgByTag.setImageDrawable(imageDrawable);
							} else {
								imgByTag.setImageResource(R.drawable.sample_face);
							}
						}
					});

			// 显示头像图片
			viewHolder.imageIcon.setImageResource(R.drawable.sample_face);
			if (cachedImage != null) {
				viewHolder.imageIcon.setImageDrawable(cachedImage);
			}
		}

		// 标题
		viewHolder.text_title.setText(entity.getBlogTitle());
		viewHolder.text_desc.setText(entity.getSummary());
		//viewHolder.text_author.setText(entity.getAuthor());
		//viewHolder.text_author.setText("");
		viewHolder.text_comments
				.setText(String.valueOf(entity.getCommentNum()));
		viewHolder.text_view.setText(String.valueOf(entity.getViewNum()));

		viewHolder.text_formatdate.setText(DateUtil.DateToChineseString(entity
				.getAddTime()));

	 

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
		TextView text_blog_id;
		ImageView icon_downloaded;
	}

	/**
	 * 得到博客列表的数据
	 * 
	 * @return
	 */
	public List<Blog> getData() {
		return mBlogList;
	}

	/**
	 * 插入数据到最前面
	 * 
	 * @param listBlog
	 */
	public void insertData(List<Blog> listBlog) {
		this.mBlogList.addAll(0, listBlog);
		this.notifyDataSetChanged();
	}

	/**
	 * 往后面增加数据
	 * 
	 * @param listBlog
	 */
	public void addData(List<Blog> listBlog) {
		this.mBlogList.addAll(listBlog);
		this.notifyDataSetChanged();
	}

	/**
	 * 移除数据
	 * 
	 * @param entity
	 */
	public void removeData(Blog entity) {
		int len = this.mBlogList.size();
		for (int i = 0; i < len; i++) {
			if (this.mBlogList.get(i).getBlogId() == entity.getBlogId()) {
				this.mBlogList.remove(i);
				this.notifyDataSetChanged();
				break;
			}
		}
	}

}
