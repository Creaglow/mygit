package com.oracle.myblog.ui;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.oracle.myblog.R;
import com.oracle.myblog.adapter.BlogListAdapter;
import com.oracle.myblog.adapter.UserListAdapter;
import com.oracle.myblog.core.ConfigUtil;
import com.oracle.myblog.db.UserHelper;
import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.entity.Users;
import com.oracle.myblog.ui.BlogActivity.PageTask;
import com.oracle.myblog.util.NetHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Administrator
 * 
 */
public class SearchActivity extends Fragment {

 
	private View currentView; 
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		currentView = inflater.inflate(R.layout.activity_search, container,
				false);
		BindControls();
		BindEvent(); 


		return currentView;
	}
	/**
	 * 找到控件
	 */
	private void BindControls() {
		txtSearch = (EditText) currentView.findViewById(R.id.txtSearch);
		txtSearch.clearFocus();
		listView = (ListView) currentView.findViewById(R.id.search_list);
		progressBar = (ProgressBar) currentView.findViewById(R.id.searchList_progressBar);
		search_btn = (ImageButton) currentView.findViewById(R.id.search_btn);
		btnItem = (ImageButton) currentView.findViewById(R.id.search_btn);
//		// 上一次查询
//		sharePreferencesSettings = getSharedPreferences(
//				res.getString(R.string.preferences_key), MODE_PRIVATE);
//		String lastSearch = sharePreferencesSettings.getString(
//				res.getString(R.string.preference_last_search_keyword), "");
//		txtSearch.setText(lastSearch);
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
			Toast.makeText(getActivity().getApplicationContext(), "字符串为空", Toast.LENGTH_SHORT);
			txtSearch.setFocusable(true);
			return;
		}
		// 保存配置
		sharePreferencesSettings
				.edit()
				.putString(
						res.getString(R.string.preference_last_search_keyword),
						q).commit();
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
			if (!NetHelper.networkIsAvaliable(getActivity().getApplicationContext()
					.getApplicationContext())) {
				Toast.makeText(getActivity().getApplicationContext(),
						R.string.sys_network_error, Toast.LENGTH_SHORT).show();
				return;
			}

			if (result == null || result.size() == 0) {// 没有新数据
				return;
			}

			listUser = result;

			progressBar.setVisibility(View.GONE);
			adapter = new UserListAdapter(getActivity().getApplicationContext(), mListView,
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

	
//	// 查看新闻Activity
//	private void RedirectAction() {
//		Intent intent = new Intent();
//		intent.setClass(getActivity(), SearchDetailActivity.class);
//
//
//		startActivity(intent);
//
//	}

	

}
