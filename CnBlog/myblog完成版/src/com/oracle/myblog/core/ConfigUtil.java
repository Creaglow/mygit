package  com.oracle.myblog.core;

/**
 * 配置内容信息
 * @author Administrator
 *
 */
public class ConfigUtil {

	/**
	 * 数据库文件名 cnblogs_db
	 */
	public static final String DB_FILE_NAME="cnblogs_db";
	
	/**
	 * 博客数据表名
	 */
	public static final String DB_BLOG_TABLE = "BlogList";
	
	/**
	 * 新闻数据表名
	 */
	public static final String DB_NEWS_TABLE = "NewsList";
	 
	
	/**
	 * 收藏表
	 */
	public static final String DB_FAV_TABLE="FavList";
	
	
	/**
	 *  有内存卡的时候，临时图片文件路径  /sdcard/cnblogs/images/
	 */ 
	public static final String TEMP_IMAGES_LOCATION = "/sdcard/cnblogs/images/";  

	/**
	 * 没有内存卡的时候,临时图片文件路径  /cnblogs/images/
	 */
	public static final String TEMP_IMAGES_LOCATION_CARD="/cnblogs/images/";
	
	
    /**
     * 全局编码方式
     */
	public static final String ENCODE_TYPE = "utf-8";   

	public static final String APP_UPDATE_URL = "http://android.walkingp.com/api/update_app.ashx?alias={alias}&action=update";

	/**
	 * 博客分页条数 (默认10 条)
	 */
	public static final int BLOG_PAGE_SIZE = 10; 
	
	/**
	 * 根据页码（从1开始) 
	 */
	public static final String URL_GET_BLOG_LIST = "http://wcf.open.cnblogs.com/blog/sitehome/paged/{pageIndex}/{pageSize}";   
	
	/**
	 * 根据编号取内容  http://wcf.open.cnblogs.com/blog/post/body/{0} 
	 */
	public static final String URL_GET_BLOG_DETAIL = "http://wcf.open.cnblogs.com/blog/post/body/{0}"; 

	/**
	 * 48小时阅读排行
	 */
	public static final String URL_48HOURS_TOP_VIEW_LIST="http://wcf.open.cnblogs.com/blog/48HoursTopViewPosts/{size}"; 
	/**
	 * 48小时阅读排行数据条数
	 */
	public static final int NUM_48HOURS_TOP_VIEW=20;
	
	/**
	 * 10天内推荐排行
	 */
	public static final String URL_TENDAYS_TOP_DIGG_LIST="http://wcf.open.cnblogs.com/blog/TenDaysTopDiggPosts/{size}"; 
	/**
	 * 10天内推荐排行数据条数
	 */
	public static final int NUM_TENDAYS_TOP_DIGG=20; 
	
	/**
	 * 新闻分页条数
	 */
	public static final int NEWS_PAGE_SIZE = 10;   
	/**
	 * 根据页码（从1开始)
	 */
	public static final String URL_GET_NEWS_LIST = "http://wcf.open.cnblogs.com/news/recent/paged/{pageIndex}/{pageSize}"; 
	/**
	 * 根据编号取内容
	 */
	public static final String URL_GET_NEWS_DETAIL = "http://wcf.open.cnblogs.com/news/item/{0}"; 
	
	public static final String URL_RECOMMEND_NEWS_LIST="http://wcf.open.cnblogs.com/news/recommend/paged/{pageIndex}/{pageSize}";//推荐新闻
	
	public static final int COMMENT_PAGE_SIZE = 10;// 评论分页条数
	
	public static final String URL_NEWS_GET_COMMENT_LIST = "http://wcf.open.cnblogs.com/news/item/{contentId}/comments/{pageIndex}/{pageSize}";// 得到新闻评论分页
	public static final String URL_BLOG_GET_COMMENT_LIST = "http://wcf.open.cnblogs.com/blog/post/{contentId}/comments/{pageIndex}/{pageSize}";// 得到博客评论分页
	
	public static final String URL_USER_SEARCH_AUTHOR_LIST = "http://wcf.open.cnblogs.com/blog/bloggers/search?t={username}";// 用户搜索
	
	public static final int NUM_RECOMMEND_USER=10;//推荐博客分页条数
	public static final String URL_RECOMMEND_USER_LIST="http://wcf.open.cnblogs.com/blog/bloggers/recommend/{pageIndex}/{pageSize}";//推荐博客排名
	
	public static final int BLOG_LIST_BY_AUTHOR_PAGE_SIZE = 10;// 博主文章列表分页
	public static final String URL_GET_BLOG_LIST_BY_AUTHOR = "http://wcf.open.cnblogs.com/blog/u/{author}/posts/{pageIndex}/{pageSize}";// 博主文章列表

	public static final String LOCAL_PATH = "file:///android_asset/";// 本地html

}
