package com.oracle.myblog.ui;

import java.io.InputStream;

import com.oracle.myblog.R;
import com.oracle.myblog.core.ConfigUtil;
import com.oracle.myblog.db.BlogDalHelper;
import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.util.DateUtil;
import com.oracle.myblog.util.NetHelper;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BlogDetailActivity extends Activity implements OnGestureListener {

	private Blog mBlog;
	private Button mBlog_button_back; // 返回按钮
	private WebView mWebView;
	private ProgressBar mBlogBody_progressBar;

	private GestureDetector gestureScanner;// 手势

	private boolean isFullScreen = false; // 是否全屏
	private RelativeLayout mRL_blog_detail;

	/**
	 * 数据库访问类
	 */
	private BlogDalHelper blogDalHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 防止休眠
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_blog_detail);

		initView();
		initData();
		initListener();

	}

	// 初始化控件
	private void initView() {
		this.mBlog_button_back = (Button) findViewById(R.id.blog_button_back);
		this.mBlogBody_progressBar = (ProgressBar) findViewById(R.id.blogbody_progressBar);
		this.mRL_blog_detail = (RelativeLayout) findViewById(R.id.rl_blog_detail);
		try {

			// WebView控件
			this.mWebView = (WebView) findViewById(R.id.blog_body_webview);
			this.mWebView.getSettings().setDefaultTextEncodingName(
					ConfigUtil.ENCODE_TYPE);// 设定编码格式
			// this.mWebView.addJavascriptInterface(new JsObject(), "javatojs");
			this.mWebView.setSelected(true);
			this.mWebView.setScrollBarStyle(0);

			WebSettings webSetting = this.mWebView.getSettings();
			webSetting.setJavaScriptEnabled(true);
			webSetting.setPluginState(PluginState.ON);
			webSetting.setNeedInitialFocus(false);
			webSetting.setSupportZoom(true);
			webSetting.setDefaultFontSize(12);
			webSetting.setCacheMode(WebSettings.LOAD_DEFAULT
					| WebSettings.LOAD_CACHE_ELSE_NETWORK);

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), R.string.sys_network_error,
					Toast.LENGTH_SHORT).show();
		}

		// 监听屏幕动作事件
		this.gestureScanner = new GestureDetector(this, this);
		this.gestureScanner.setIsLongpressEnabled(true);
		// 双击事件
		this.gestureScanner.setOnDoubleTapListener(new OnDoubleTapListener() {

			// 用来判定该次点击是SingleTap而不是DoubleTap
			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				return false;
			}

			// 通知DoubleTap手势中的事件，包含down、up和move事件
			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				return false;
			}

			// 在双击的第二下，Touch down时触发
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				if (!isFullScreen) {
					setFullScreen();
				} else {
					quitFullScreen();
				}
				isFullScreen = !isFullScreen;

				return false;
			}
		});

	}

	// 初始化数据
	private void initData() {
		// 传递过来的值
		this.mBlog = (Blog) getIntent().getSerializableExtra("blog");

		this.blogDalHelper = new BlogDalHelper(this);

		String url = ConfigUtil.URL_GET_BLOG_DETAIL.replace("{0}",
				String.valueOf(mBlog.getBlogId()));// 网址

		PageTask task = new PageTask();
		task.execute(url);

	}

	// 初始化监听
	private void initListener() {

		// 点击返回
		this.mBlog_button_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BlogDetailActivity.this.finish();
			}
		});

		// 双击全屏
		this.mWebView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureScanner.onTouchEvent(event);
			}
		});
	}

	/**
	 * 全屏
	 */
	private void setFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.mRL_blog_detail.setVisibility(View.GONE);
	}

	/**
	 * 退出全屏
	 */
	private void quitFullScreen() {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow()
				.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		this.mRL_blog_detail.setVisibility(View.VISIBLE);
	}

	/**
	 * 加载内容
	 * 
	 * @param webView
	 * @param content
	 */
	private void loadWebViewContent(WebView webView, String content) {
		webView.loadDataWithBaseURL(ConfigUtil.LOCAL_PATH, content,
				"text/html", ConfigUtil.ENCODE_TYPE, null);
	}

	public class PageTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			mBlogBody_progressBar.setVisibility(View.VISIBLE);
		}

		// 可变长的输入参数，与AsyncTask.exucute()对应
		@Override
		protected String doInBackground(String... params) {
			String _blogContent = blogDalHelper.getBlogContentById(mBlog
					.getBlogId());
			return _blogContent;
		}

		// 加载内容到控件中去
		@Override
		protected void onPostExecute(String result) {
			Log.d("BlogDetailActivity", result);
			String htmlContent = "";

			try {
				InputStream in = getAssets().open("NewsDetail.html");

				byte[] temp = NetHelper.readInputStream(in);
				htmlContent = new String(temp);

				StringBuffer str = new StringBuffer();
				str.append("作者:");
				str.append(mBlog.getAuthor());
				str.append("  发表时间：");
				str.append(DateUtil.parseDateToString(mBlog.getAddTime(),
						"yyyy-MM-dd"));
				str.append(" 浏览次数:");
				str.append(mBlog.getViewNum());

				// 替换网页模板内容
				htmlContent = htmlContent
						.replace("#title#", mBlog.getBlogTitle())
						.replace("#time#", str.toString())
						.replace("#content#", result);

				loadWebViewContent(mWebView, htmlContent);

				// 隐藏进度条
				mBlogBody_progressBar.setVisibility(View.GONE);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}

}
