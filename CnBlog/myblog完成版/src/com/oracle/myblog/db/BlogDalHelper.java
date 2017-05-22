package com.oracle.myblog.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.oracle.myblog.core.ConfigUtil;
import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.util.DateUtil;
import com.oracle.myblog.util.NetHelper;
import com.oracle.myblog.util.StringUtil;
import com.oracle.myblog.xml.BlogXmlParser;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings.Global;
import android.util.Log;

/**
 * 博客数据帮助类
 * 
 * @author Administrator
 * 
 */
public class BlogDalHelper {

	private DatabaseHelper dbHelper;

	private SQLiteDatabase db;

	private final static byte[] _writeLock = new byte[0]; // 同步锁

	public BlogDalHelper(Context context) {
		dbHelper = new DatabaseHelper(context); // context应该是activity的对象
		db = dbHelper.getWritableDatabase(); // getWritableDatabase取得的实例是以读写的方式打开数据库
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * 判断是否已经存在
	 * 
	 * @param blogId
	 * @return
	 */
	private boolean exists(int blogId) {
		String selection = " BlogId=?";
		String[] args = { String.valueOf(blogId) };

		Cursor cursor = db.query(ConfigUtil.DB_BLOG_TABLE, null, selection,
				args, null, null, null);
		boolean isExists = cursor != null && cursor.moveToNext();
		cursor.close();
		return isExists;
	}

	/**
	 * 得到头条
	 * 
	 * @return
	 */
	public List<Blog> getTopBlogList() {
		return getBlogList("10", "", null);
	}

	/**
	 * 分页查询
	 * 
	 * @param pageIndex
	 *            当前页数
	 * @param pageSize
	 *            每几多少笔
	 * @return
	 */
	public List<Blog> getBlogByPage(int pageIndex, int pageSize) {
		String limit = String.valueOf((pageIndex - 1) * pageSize) + ","
				+ String.valueOf(pageSize);
		return getBlogList(limit, null, null);
	}

	/**
	 * 查询ID查询博客信息
	 * 
	 * @param blogId
	 * @return
	 */
	public Blog getBlogById(int blogId) {
		String limit = "1";
		String selection = "BlogId=?";
		String[] args = { String.valueOf(blogId) };
		List<Blog> arr = getBlogList(limit, selection, args);
		if (arr.size() > 0) {
			return arr.get(0);
		}
		return null;
	}

	/**
	 * 查询数据
	 * 
	 * @param limit
	 *            分页条件
	 * @param selection
	 *            查询条件
	 * @param selectionArgs
	 *            查询条件的值
	 * @return
	 */
	public List<Blog> getBlogList(String limit, String selection,
			String[] selectionArgs) {
		List<Blog> listBlog = new ArrayList<Blog>();

		String orderBy = " BlogID desc"; // 排序方式

		Cursor cursor = db.query(ConfigUtil.DB_BLOG_TABLE, null, selection,
				selectionArgs, null, null, orderBy, limit);

		while (cursor != null && cursor.moveToNext()) {
			Blog entity = new Blog();
			String addTimeStr = cursor.getString(cursor  
					.getColumnIndex("Published"));  //获取该行指定列的数据

			Date addTime = DateUtil.parseDate(addTimeStr);

			entity.setAddTime(addTime);
			entity.setAuthor(cursor.getString(cursor
					.getColumnIndex("AuthorName")));
			entity.setAuthorUrl(cursor.getString(cursor
					.getColumnIndex("AuthorUrl")));
			entity.setAvator(cursor.getString(cursor
					.getColumnIndex("AuthorAvatar")));
			entity.setBlogContent(cursor.getString(cursor
					.getColumnIndex("Content")));

			entity.setBlogId(cursor.getInt(cursor.getColumnIndex("BlogId")));
			entity.setBlogTitle(cursor.getString(cursor
					.getColumnIndex("BlogTitle")));
			String blogUrl = "";
			if (cursor.getString(cursor.getColumnIndex("BlogUrl")) != null) {
				blogUrl = cursor.getString(cursor.getColumnIndex("BlogUrl"));
			}
			entity.setBlogUrl(blogUrl);

			entity.setCommentNum(cursor.getInt(cursor
					.getColumnIndex("Comments")));
			entity.setDiggsNum(cursor.getInt(cursor.getColumnIndex("Digg")));

			entity.setSummary(cursor.getString(cursor.getColumnIndex("Summary")));

			Date updateTime = new Date();
			if (cursor.getString(cursor.getColumnIndex("Updated")) != null) {
				updateTime = DateUtil.parseDate(cursor.getString(cursor
						.getColumnIndex("Updated")));
			}
			entity.setUpdateTime(updateTime);
			entity.setViewNum(cursor.getInt(cursor.getColumnIndex("View")));
			boolean isRead = cursor
					.getString(cursor.getColumnIndex("IsReaded")).equals("1");

			entity.setIsReaded(isRead);

			listBlog.add(entity);
		}
		cursor.close();
		return listBlog;
	}

	/**
	 * 插入数据到Blog数据表中
	 * 
	 * @param bloglist
	 */
	public void insertBlogDataToDB(List<Blog> bloglist) {
		// ContentValues类和Hashtable比较类似，它也是负责存储一些名值对，
		// 但是它存储的名值对当中的名是一个String类型，而值都是基本类型

		List<ContentValues> list = new ArrayList<ContentValues>();

		for (Blog b : bloglist) {
			ContentValues insertValues = new ContentValues();

			insertValues.put("BlogId", b.getBlogId());
			insertValues.put("BlogTitle", b.getBlogTitle());
			insertValues.put("Summary", b.getSummary());

			String context = ""; // 内容
			if (!StringUtil.isEmpty(b.getBlogContent())) {
				context = b.getBlogContent();
			}
			insertValues.put("Content", context);
			insertValues.put("Published",
					DateUtil.parseDateToString(b.getAddTime()));

			String updateTime = "";
			if (b.getUpdateTime() != null) {
				updateTime = DateUtil.parseDateToString(b.getUpdateTime());
			} else {
				updateTime = DateUtil.parseDateToString(new Date()); // 当前时间
			}
			insertValues.put("Updated", updateTime);
			insertValues.put("AuthorName", b.getAuthor());
			insertValues.put("AuthorAvatar", b.getAuthor()); // 作者头像

			// 作者头像地睛
			String authorUrl = "";
			if (!StringUtil.isEmpty(b.getAuthorUrl())) {
				authorUrl = b.getAuthorUrl();
			}
			insertValues.put("AuthorUrl", authorUrl);
			insertValues.put("View", b.getViewNum());
			insertValues.put("Comments", b.getCommentNum());
			insertValues.put("Digg", b.getDiggsNum());
			insertValues.put("IsReaded", false); // 默认未读

			insertValues.put("BlogUrl", b.getBlogUrl());

			// 添加到集合中
			list.add(insertValues);
		}

		// 开始保存数据,同步锁
		synchronized (_writeLock) {

			db.beginTransaction(); // 开始事务
			try {

				for (ContentValues c : list) {
					int blogId = c.getAsInteger("BlogId");
					// 判断该blogId 条信息是否存在
					boolean isExists = exists(blogId);

					// 如果该条信息不存在就直接写入
					if (!isExists) {
						db.insert(ConfigUtil.DB_BLOG_TABLE, null, c);
					}
				}

				db.setTransactionSuccessful(); // 提交事务

			} catch (Exception e) {
				Log.d("BlogActivity", e.getMessage());
			} finally {
				db.endTransaction(); // 结束事务
			}
		}
	}

	/**
	 * 标识该条博客信息已读
	 * 
	 * @param blogId
	 */
	public void markBlogInfoAsReaded(int blogId) {
		String sql = "update " + ConfigUtil.DB_BLOG_TABLE
				+ "  set IsReaded=1 where BlogId=?";
		String[] args = { String.valueOf(blogId) };
		db.execSQL(sql, args);
	}

	/**
	 * 根据博客ID,更新博客的内容
	 * 
	 * @param blogId
	 */
	public void updateContentByBlogId(int blogId, String htmlContent) {

		String sql = "update " + ConfigUtil.DB_BLOG_TABLE
				+ "  set  Content=? where BlogId=?";
		String[] args = { htmlContent, String.valueOf(blogId) };

		db.execSQL(sql, args);
	}

	/**
	 * 该条信息是否已读
	 * 
	 * @param blogId
	 */
	public boolean getIsReadBlogInfo(int blogId) {
		Blog entity = getBlogById(blogId);

		if (entity == null) {
			return false;
		}

		return entity.getIsReaded();

	}

	/**
	 * 根据页码返回Blog对象集合,根据网页访问的数据,把xml解析成Blog对象
	 * 
	 * @param pageIndex
	 *            页码，从1开始
	 * @return
	 */
	public static ArrayList<Blog> getBlogList(int pageIndex) {
		int pageSize = ConfigUtil.BLOG_PAGE_SIZE;

		String url = ConfigUtil.URL_GET_BLOG_LIST.replace("{pageIndex}",
				String.valueOf(pageIndex)).replace("{pageSize}",
				String.valueOf(pageSize));

		String dataString = NetHelper.getContentFromUrl(url);

		ArrayList<Blog> arr = new ArrayList<Blog>();
		if (dataString != "") {
			arr = BlogXmlParser.parseBlogString(dataString);
		}

		return arr;
	}

	/**
	 * 根据编号获取数据库中博客内容
	 * 
	 * @param blogId
	 * @return
	 */
	public String getBlogContentById(int blogId) {
		String blogContent = "";

		// 先查询本地数据库是否存在该信息
		Blog entity = this.getBlogById(blogId);

		blogContent = entity.getBlogContent();

		// 判断内容是否为空null
		if (StringUtil.isEmpty(blogContent)) {

			// 解析xml,把内容放到数据库中去
			blogContent = getBlogWithNetWork(blogId);

			// 放到数据库里面去
			updateContentByBlogId(blogId, blogContent);
		}

		return blogContent;
	}

	public String getBlogWithNetWork(int blogId) {

		String url = ConfigUtil.URL_GET_BLOG_DETAIL.replace("{0}",
				String.valueOf(blogId));// 网址

		// 根据地址,取得网页上的xml信息
		String dataString = NetHelper.getContentFromUrl(url);

		// 把xml信息解析成字符串
		String htmlContent = BlogXmlParser.parseBlogContentString(dataString);

		return htmlContent;
	}
}