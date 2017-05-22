package com.oracle.myblog.ui;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oracle.myblog.R;
import com.oracle.myblog.adapter.UserListAdapter;
import com.oracle.myblog.db.UserHelper;
import com.oracle.myblog.entity.Users;
import com.oracle.myblog.util.NetHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SearchDetailActivity extends Activity implements OnGestureListener {
	private EditText txtSearch;
	private List<Users> listUser = new ArrayList<Users>();

	private UserListAdapter adapter;

	private String q;// 搜索词
  
	private ListView listView;
	private PullToRefreshListView mListView;
	private ImageButton search_btn;// 搜索按钮

	private ProgressBar progressBar;// 加载
	private Resources res;// 资源
	private SharedPreferences sharePreferencesSettings;// 设置

	private ImageButton btnItem;// 按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 防止休眠
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_search_detail);
		BindControls();
		BindEvent();

	}

	/**
	 * 找到控件
	 */
	private void BindControls() {
		txtSearch = (EditText) findViewById(R.id.txtSearch);
		txtSearch.clearFocus();
		listView = (ListView) findViewById(R.id.search_list);
		progressBar = (ProgressBar) findViewById(R.id.searchList_progressBar);
		search_btn = (ImageButton) findViewById(R.id.search_btn);
		btnItem = (ImageButton) findViewById(R.id.search_btn);
		// 上一次查询
		sharePreferencesSettings = getSharedPreferences(
				res.getString(R.string.preferences_key), MODE_PRIVATE);
		String lastSearch = sharePreferencesSettings.getString(
				res.getString(R.string.preference_last_search_keyword), "");
		txtSearch.setText(lastSearch);
		// 回车搜索 
		txtSearch.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.KEYCODE_ENTER == keyCode) {
					StartSearch();
					return true;
				}
				return false;
			}
		});

	}

	private void BindEvent() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 执行搜索
	 */
	private void StartSearch() {
		q = txtSearch.getText().toString();
		if (q.equals("")) {
			Toast.makeText(getApplicationContext(), "字符串为空", Toast.LENGTH_SHORT);
			txtSearch.setFocusable(true);
			return;
		}
//		// 保存配置
//		sharePreferencesSettings
//				.edit()
//				.putString(
//						res.getString(R.string.preference_last_search_keyword),
//						q).commit();
		new PageTask().execute();
	}

	/**
	 * 多线程启动（用于上拉加载、初始化、下载加载、刷新）
	 * 
	 */
	public class PageTask extends AsyncTask<String, Integer, List<Users>> {
		public PageTask() {
		}

		protected List<Users> doInBackground(String... params) {

			try {
				List<Users> listTmp = new ArrayList<Users>();
				List<Users> listUserNew = UserHelper.GetUserList(q);
				int size = listUserNew.size();
				for (int i = 0; i < size; i++) {
					if (!listUser.contains(listUserNew.get(i))) {// 避免出现重复
						listTmp.add(listUserNew.get(i));
					}
				}
				return listTmp;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		/**
		 * 加载内容
		 */
		@Override
		protected void onPostExecute(List<Users> result) {
			// 网络不可用
			if (!NetHelper.networkIsAvaliable(getApplicationContext()
					.getApplicationContext())) {
				Toast.makeText(getApplicationContext().getApplicationContext(),
						R.string.sys_network_error, Toast.LENGTH_SHORT).show();
				return;
			}

			if (result == null || result.size() == 0) {// 没有新数据
				return;
			}

			listUser = result;

			progressBar.setVisibility(View.GONE);
			adapter = new UserListAdapter(getApplicationContext(), mListView,
					listUser);
			listView.setAdapter(adapter);
		}

		@Override
		protected void onPreExecute() {
			// 主体进度条
			if (listView.getCount() == 0) {
				progressBar.setVisibility(View.VISIBLE);
			}
		}

		@Override  
		protected void onProgressUpdate(Integer... values) {
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
