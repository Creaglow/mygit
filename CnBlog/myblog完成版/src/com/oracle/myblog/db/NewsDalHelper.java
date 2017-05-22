package com.oracle.myblog.db;

	import java.util.ArrayList;
import java.util.Date;
import java.util.List;

	import com.oracle.myblog.core.ConfigUtil;
import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.entity.News;
import com.oracle.myblog.util.DateUtil;
import com.oracle.myblog.util.NetHelper;
import com.oracle.myblog.util.StringUtil;
import com.oracle.myblog.xml.BlogXmlParser;
import com.oracle.myblog.xml.NewsXmlParser;

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

public class NewsDalHelper {


		private DatabaseHelper dbHelper;

		private SQLiteDatabase db;

		private final static byte[] _writeLock = new byte[0]; // 同步锁

		public NewsDalHelper(Context context) {
			dbHelper = new DatabaseHelper(context);
			db = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		/**
		 * 判断是否已经存在
		 * 
		 * @param newsId
		 * @return
		 */
		private boolean exists(int newsId) {
			String selection = " NewsId=?";
			String[] args = { String.valueOf(newsId) };

			Cursor cursor = db.query(ConfigUtil.DB_NEWS_TABLE, null, selection,
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
		public List<News> getTopNewsList() {
			return getNewsList("10", "", null);
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
		public List<News> getNewsByPage(int pageIndex, int pageSize) {
			String limit = String.valueOf((pageIndex - 1) * pageSize) + ","
					+ String.valueOf(pageSize);
			return getNewsList(limit, null, null);
		}

		/**
		 * 查询ID查询新闻信息
		 * 
		 * @param newsId
		 * @return
		 */
		public News getNewsById(int newsId) {
			String limit = "1";
			String selection = "NewsId=?";
			String[] args = { String.valueOf(newsId) };
			List<News> arr = getNewsList(limit, selection, args);
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
		public List<News> getNewsList(String limit, String selection,
				String[] selectionArgs) {
			List<News> listNews = new ArrayList<News>();

			String orderBy = " NewsID desc"; // 排序方式

			Cursor cursor = db.query(ConfigUtil.DB_NEWS_TABLE, null, selection,
					selectionArgs, null, null, orderBy, limit);

			while (cursor != null && cursor.moveToNext()) {
				News entity = new News();
				String addTimeStr = cursor.getString(cursor
						.getColumnIndex("Published"));

				Date addTime = DateUtil.parseDate(addTimeStr);

			
			
				
				entity.setUpdateTime(addTime);
				entity.setNewsContent(cursor.getString(cursor
						.getColumnIndex("Content")));

				entity.setNewsId(cursor.getInt(cursor.getColumnIndex("NewsId")));
				entity.setNewsTitle(cursor.getString(cursor
						.getColumnIndex("NewsTitle")));
				String newsUrl = "";
				if (cursor.getString(cursor.getColumnIndex("NewsUrl")) != null) {
					newsUrl = cursor.getString(cursor.getColumnIndex("NewsUrl"));
				}
				entity.setNewsUrl(newsUrl);

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
	 

				listNews.add(entity);
			}
			cursor.close();
			return listNews;
		}

		/**
		 * 插入数据到News数据表中
		 * 
		 * @param Newslist
		 */
		public void insertNewsDataToDB(List<News> newslist) {
			// ContentValues类和Hashtable比较类似，它也是负责存储一些名值对，
			// 但是它存储的名值对当中的名是一个String类型，而值都是基本类型

			List<ContentValues> list = new ArrayList<ContentValues>();

			for (News b : newslist) {
				ContentValues insertValues = new ContentValues();

				insertValues.put("NewsId", b.getNewsId());
				insertValues.put("NewsTitle", b.getNewsTitle());
				insertValues.put("Summary", b.getSummary());

				String context = ""; // 内容
				if (!StringUtil.isEmpty(b.getNewsContent())) {
					context = b.getNewsContent();
				}
				insertValues.put("Content", context);
				
				String updateTime = "";
				if (b.getUpdateTime() != null) {
					updateTime = DateUtil.parseDateToString(b.getUpdateTime());
				} else {
					updateTime = DateUtil.parseDateToString(new Date()); // 当前时间
				}
				insertValues.put("Updated", updateTime);
				
				
				String publishedTime = "";
				if (b.getPublished() != null) {
					publishedTime = DateUtil.parseDateToString(b.getPublished());
				} else {
					publishedTime = DateUtil.parseDateToString(new Date()); // 当前时间
				}
		        insertValues.put("Published", publishedTime);

			  
				
				insertValues.put("View", b.getViewNum());
				insertValues.put("Comments", b.getCommentNum());
				insertValues.put("Digg", b.getDiggsNum());
				insertValues.put("IsReaded", false); // 默认未读

				insertValues.put("NewsUrl", b.getNewsUrl());
			 

				// 添加到集合中
				list.add(insertValues);
			}

			// 开始保存数据,同步锁
			synchronized (_writeLock) {

				db.beginTransaction(); // 开始事务
				try {

					for (ContentValues c : list) {
						int newsId = c.getAsInteger("NewsId");
						// 判断该newsId 条信息是否存在
						boolean isExists = exists(newsId);

						// 如果该条信息不存在就直接写入
						if (!isExists) {
							db.insert(ConfigUtil.DB_NEWS_TABLE, null, c);
						}
					}

					db.setTransactionSuccessful(); // 提交事务

				} catch (Exception e) {
					Log.d("NewsActivity", e.getMessage());
				} finally {
					db.endTransaction(); // 结束事务
				}
			}
		}

		/**
		 * 标识该条博客信息已读
		 * 
		 * @param newsId
		 */
		public void markNewsInfoAsReaded(int newsId) {
			String sql = "update " + ConfigUtil.DB_NEWS_TABLE
					+ "  set IsReaded=1 where NewsId=?";
			String[] args = { String.valueOf(newsId) };
			db.execSQL(sql, args);
		}
		/**
		 * 根据博客ID,更新博客的内容
		 * 
		 * @param NewsId
		 */
		public void updateContentByNewsId(int newsId, String htmlContent) {

			String sql = "update " + ConfigUtil.DB_NEWS_TABLE
					+ "  set  Content=? where NewsId=?";
			String[] args = { htmlContent, String.valueOf(newsId) };

			db.execSQL(sql, args);
		}


		/**
		 * 该条信息是否已读
		 * 
		 * @param NewsId
		 */
		public boolean getIsReadNewsInfo(int newsId) {
			News entity = getNewsById(newsId);

			if (entity == null) {
				return false;
			}

			return entity.getIsReaded();

		}

		/**
		 * 根据页码返回News对象集合,根据网页访问的数据,把xml解析成News对象
		 * 
		 * @param pageIndex
		 *            页码，从1开始
		 * @return
		 */
		public static ArrayList<News> getNewsList(int pageIndex) {
			int pageSize = ConfigUtil.NEWS_PAGE_SIZE;

			String url = ConfigUtil.URL_GET_NEWS_LIST.replace("{pageIndex}",
					String.valueOf(pageIndex)).replace("{pageSize}",
					String.valueOf(pageSize));

			String dataString = NetHelper.getContentFromUrl(url);

			ArrayList<News> arr = new ArrayList<News>();
			if (dataString != "") {
				arr = NewsXmlParser.parseNewsString(dataString);
			}

			return arr;
		}

		/**
		 * 根据编号获取数据库中博客内容
		 * 
		 * @param newsId
		 * @return
		 */
		public String getNewsContentById(int newsId) {
			String newsContent = "";

			// 先查询本地数据库是否存在该信息
			News entity = this.getNewsById(newsId);

			newsContent = entity.getNewsContent();
			// 判断内容是否为空null
			if (StringUtil.isEmpty(newsContent)) {

				// 解析xml,把内容放到数据库中去
				newsContent = getNewsWithNetWork(newsId);

				// 放到数据库里面去
				updateContentByNewsId(newsId, newsContent);
			}


			return newsContent;
		}
		public String getNewsWithNetWork(int newsId) {

			String url = ConfigUtil.URL_GET_NEWS_DETAIL.replace("{0}",
					String.valueOf(newsId));// 网址

			// 根据地址,取得网页上的xml信息
			String dataString = NetHelper.getContentFromUrl(url);

			// 把xml信息解析成字符串
			String htmlContent = NewsXmlParser.parseNewsContentString(dataString);

			return htmlContent;
		}

	}
