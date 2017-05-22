package com.oracle.myblog.db;

import com.oracle.myblog.core.ConfigUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log; 

public class DatabaseHelper extends SQLiteOpenHelper {
	// 定义数据库文件
	private static final String DB_NAME = ConfigUtil.DB_FILE_NAME;
	// 定义数据库版本
	private static final int DB_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CreateBlogDb(db);
		Log.i("DBHelper", "创建BlogList表成功");
		CreateNewsDb(db);
		Log.i("DBHelper", "创建NewsList表成功"); 	  
		CreateFavListDb(db);
		Log.i("DBHelper", "创建FavList表成功");
	}

	/**
	 * 创建BlogList表
	 * 
	 * @param db
	 */
	private void CreateBlogDb(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE [BlogList] (");
		sb.append("[BlogId] INTEGER(13) NOT NULL DEFAULT (0), ");
		sb.append("[BlogTitle] NVARCHAR(50) NOT NULL DEFAULT (''), ");
		sb.append("[Summary] NVARCHAR(500) NOT NULL DEFAULT (''), ");
		sb.append("[Content] NTEXT NOT NULL DEFAULT (''), ");
		sb.append("[Published] DATETIME, ");
		sb.append("[Updated] DATETIME, ");
		sb.append("[AuthorUrl] NVARCHAR(200), ");
		sb.append("[AuthorName] NVARCHAR(50), ");
		sb.append("[AuthorAvatar] NVARCHAR(200), "); //头像
		sb.append("[View] INTEGER(16) DEFAULT (0), ");
		sb.append("[Comments] INTEGER(16) DEFAULT (0), ");
		sb.append("[Digg] INTEGER(16) DEFAULT (0), ");
		sb.append("[IsReaded] BOOLEAN DEFAULT (0), "); 
		sb.append("[BlogUrl] NVARCHAR(200));");// 网页地址   
		db.execSQL(sb.toString());
	}

	/**
	 * 创建NewsList表
	 * 
	 * @param db
	 */
	private void CreateNewsDb(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE [NewsList] (");
		sb.append("[NewsId] INTEGER(13) NOT NULL DEFAULT (0), ");
		sb.append("[NewsTitle] NVARCHAR(50) NOT NULL DEFAULT (''), ");
		sb.append("[Summary] NVARCHAR(500) NOT NULL DEFAULT (''), ");
		sb.append("[Content] NTEXT NOT NULL DEFAULT (''), ");
		sb.append("[Published] DATETIME, ");
		sb.append("[Updated] DATETIME, ");
		sb.append("[View] INTEGER(16) DEFAULT (0), ");
		sb.append("[Comments] INTEGER(16) DEFAULT (0), ");
		sb.append("[Digg] INTEGER(16) DEFAULT (0), ");
		sb.append("[IsReaded] BOOLEAN DEFAULT (0), ");  
		sb.append("[NewsUrl] NVARCHAR(200)); ");// 网页地址 

		db.execSQL(sb.toString());
	}

  

	/**
	 * 创建收藏表FavList
	 * 
	 * @param db
	 */
	private void CreateFavListDb(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE [FavList] (");
		sb.append("[FavId] INTEGER PRIMARY KEY AUTOINCREMENT,");
		sb.append("[AddTime] DATETIME NOT NULL DEFAULT (date('now')), ");
		sb.append("[ContentType] INTEGER NOT NULL DEFAULT (0),");
		sb.append("[ContentId] INTEGER NOT NULL DEFAULT (0));");
		db.execSQL(sb.toString());
	}

	/**
	 * 更新版本时更新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		DropTable(db);
		onCreate(db);
		Log.e("User", "onUpgrade");
	}

	/**
	 * 删除表
	 * 
	 * @param db
	 */
	private void DropTable(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE IF EXISTS " + ConfigUtil.DB_BLOG_TABLE + ";");
		sb.append("DROP TABLE IF EXISTS " + ConfigUtil.DB_NEWS_TABLE + ";"); 
		sb.append("DROP TABLE IF EXISTS " + ConfigUtil.DB_FAV_TABLE + ";");
		db.execSQL(sb.toString());
	}

	/**
	 * 清空数据表（仅清空无用数据）
	 * 
	 * @param db
	 */
	public static void ClearData(Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM FavList; ");// 清空新闻表 
		sb.append("DELETE FROM BlogList;");// 清空博客表
		sb.append("DELETE FROM NewsList; ");// 清空新闻表 		 
		
		db.execSQL(sb.toString());
	}
}
