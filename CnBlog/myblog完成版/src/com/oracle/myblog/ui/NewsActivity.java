package com.oracle.myblog.ui;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.oracle.myblog.R;
import com.oracle.myblog.adapter.BlogListAdapter;
import com.oracle.myblog.adapter.NewsListAdapter;
import com.oracle.myblog.core.ConfigUtil;
import com.oracle.myblog.db.BlogDalHelper;
import com.oracle.myblog.db.NewsDalHelper;
import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.entity.News;
import com.oracle.myblog.ui.BlogActivity.PageTask;
import com.oracle.myblog.util.NetHelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



/**
 *
 * @author Administrator
 *
 */
public class NewsActivity extends Fragment{
	private List<News> listNews=new ArrayList<News>(); //存放所有博客住处
	private int pageIndex=1;   //当前第一页
	
	private PullToRefreshListView mListView;
	
	//数据匹配器
	private NewsListAdapter mNewsListAdapter;
	
	private ProgressBar mNewsBody_progressbar;
	private ProgressBar mTop_progressbar;
	private ImageButton mTopRefresh_btn;
	
 
	private NewsDalHelper dalHelper; //sqlite News_List表数据访类
	
	private View  currentView; 

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 
		 currentView=inflater.inflate(R.layout.activity_news, container,false);
		
		 initView();
		 setEventListener();
		 
		 new PageTask(0).execute();
		 
		return currentView;
		
	}
	
	
	//初始化控件
		private void initView(){
			
			mListView=(PullToRefreshListView) currentView.findViewById(R.id.pull_list);
			this.mNewsBody_progressbar=(ProgressBar) currentView.findViewById(R.id.progress_content);
			this.mTop_progressbar=(ProgressBar) currentView.findViewById(R.id.topProgressBar1);
			this.mTopRefresh_btn=(ImageButton) currentView.findViewById(R.id.topRefresh_btn1);
			                                                            
			dalHelper=new NewsDalHelper(this.getActivity());
			
		}
		
		//设定事件
		private void setEventListener(){
			
			//异步的  AsyncTask
			
			//刷新按钮
			this.mTopRefresh_btn.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View arg0) {
					new PageTask(1).execute();
					
				}
			});
			
			
			//设定上拉和下拉刷新事件
			this.mListView.setOnRefreshListener(new OnRefreshListener2() {

			    //下拉刷新
				@Override
				public void onPullDownToRefresh(PullToRefreshBase refreshView) {
					new PageTask(-1).execute();
					
				}

				//上拉刷新
				@Override
				public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				 
				 
					//当前页数加1
					pageIndex++;
					new PageTask(pageIndex).execute();
				}
				
			});
			
			//点击某一个ListView显示详细内容
			this.mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position,
						long id) {
					 
					Log.d("NewsActivity", "点击了下标:"+position);
					RedirectAction(position-1);
				}
			});
			
			
		
	
		}
		
		//多线程启动（ -1：上拉加载    0:初始化   1:刷新   2:下载加载 ）
		//异步取得值,更新到ListView界面
		public class PageTask extends AsyncTask<String,Integer, List<News>>{

			
			boolean isRefresh = false;
			int curPageIndex = 0; //当前状态
			boolean isLocalData = false; // 是否是从本地读取数据

			/**
			 * 多线程启动（用于上拉加载、初始化、下载加载、刷新）
			 * 
			 * @param page
			 *            -1：上拉加载 0:初始化 1:刷新 2:下载加载
			 */
			public PageTask(int page) {
			
				this.curPageIndex = page;
			}

			// 2、执行操作
			@Override
			protected List<News> doInBackground(String... params) {

				//判断是否有网络
				boolean isNetWorkAvailable = NetHelper
						.networkIsAvaliable(getActivity().getApplicationContext());

				int _pageIndex = curPageIndex;
				if (_pageIndex <= 0) {
					_pageIndex = 1;
				}

				// 优先读取本地数据
				List<News> listNewsLocal = dalHelper.getNewsByPage(_pageIndex,
						ConfigUtil.NEWS_PAGE_SIZE);

				// 有使用网络的情况,就解析xml
				if (isNetWorkAvailable) {

					List<News> listNewsNew = dalHelper.getNewsList(_pageIndex);
					
					return LoaddoInBackground(listNewsNew, curPageIndex);
				  
				} else {
					// 没有网络的情况
					isLocalData = true;
					if (curPageIndex == -1) { // 上拉不加载数据
						return null;
					}
					return listNewsLocal;
				}

			}

			// 3、当doInBackground执行完后，马上会执行
			@Override
			protected void onPostExecute(List<News> result) {
				// 右上角	 
				  mTop_progressbar.setVisibility(View.GONE);
				  mTopRefresh_btn.setVisibility(View.VISIBLE);  //右上角按钮显示

				Log.d("NewsActivity", curPageIndex + "，onPostExecute下拉状态");

				// 判断网络不可用并且本地没有保存数据
				if (result == null || result.size() == 0) {
					mListView.onRefreshComplete();

					// 在没有网络的情况下，使用下拉操作
					if (!NetHelper.networkIsAvaliable(getActivity())
							&& curPageIndex > 1) {
						Toast.makeText(getActivity(), R.string.sys_network_error,
								Toast.LENGTH_SHORT).show();
					}

					return;
				}

				// 如果查询到的是从网络上面取的结果，保存到数据库
				if (!isLocalData) {
					dalHelper.insertNewsDataToDB(result);
				}

				// 代表上拉刷新
				if (curPageIndex == -1) {
					mNewsListAdapter.insertData(result);

				} else if (curPageIndex == 0) {// 首次加载
					listNews = result;

					// 隐藏进度条
					mNewsBody_progressbar.setVisibility(View.GONE);
					
					mNewsListAdapter = new NewsListAdapter(getActivity(),
							mListView, listNews);
					mListView.setAdapter(mNewsListAdapter);

				} else if (curPageIndex == 1) { // 刷新操作
					try {
						// 避免首页无网络加载，按刷新按钮
						listNews = result;

						if (mNewsListAdapter != null
								&& mNewsListAdapter.getData() != null) {
							mNewsListAdapter.getData().clear(); // 清空原先的数据
							mNewsListAdapter.addData(listNews);

						} else if (result != null) {
							mNewsListAdapter = new NewsListAdapter(getActivity(),
									mListView, listNews);
							mListView.setAdapter(mNewsListAdapter);
						}

					} catch (Exception ex) {
						Log.d("NewsActivity", ex.getMessage());
					}
				} else {
					// 下拉操作
					mNewsListAdapter.addData(result);
				}

				mListView.onRefreshComplete();

				super.onPostExecute(result);
			}

			// 1、预先操作
			@Override
			protected void onPreExecute() {

				if (listNews.size() == 0) {
					mNewsBody_progressbar.setVisibility(View.VISIBLE);
				}

				// 右上角进度条显示，刷新按钮隐藏
				mTop_progressbar.setVisibility(View.VISIBLE);
				mTopRefresh_btn.setVisibility(View.GONE);

				super.onPreExecute();
			}  
			
			
		}
		
		

		/**
		 * 下拉控件加载数据判断
		 * 
		 * @param listNewsNew
		 * @param curPageIndex
		 *            -1：上拉加载 0:初始化 1:刷新 2:下载加载
		 * @return
		 */
		private List<News> LoaddoInBackground(List<News> listNewsNew,
				int curPageIndex) {
			switch (curPageIndex) {
			case -1: // 上拉
				List<News> listTemp = new ArrayList<News>();

				// 避免首页无数据时
				if (listNews != null && listNews.size() > 0) {
					// 有数据，把数据合并在temp中
					if (listNewsNew != null && listNewsNew.size() > 0) {
						int len = listNewsNew.size();
						for (int i = 0; i < len; i++) {
							if (!listNews.contains(listNewsNew.get(i))) {
								listTemp.add(listNewsNew.get(i));
							}
						}
					}
				}
				return listTemp;

			case 0: // 首次加载
			case 1: // 刷新
				if (listNewsNew != null && listNewsNew.size() > 0) {
					return listNewsNew;
				}
				break;
			default: // 下拉
				List<News> listT = new ArrayList<News>();
				if (listNews != null && listNews.size() > 0) {
					if (listNewsNew != null && listNewsNew.size() > 0) {
						int size = listNewsNew.size();
						for (int i = 0; i < size; i++) {
							if (!listNews.contains(listNewsNew.get(i))) {
								listT.add(listNewsNew.get(i));
							}
						}
					}
				}
				return listT;
			}
			return null;
		}
		
		//查看新闻Activity
				private void RedirectAction(int index){
					Intent  intent=new Intent();
					intent.setClass(getActivity(),NewsDetailActivity.class);
					
					//把当前要查看 News对象要传过去
					News  news=mNewsListAdapter.getData().get(index);
					intent.putExtra("news",news);
					
					//如果当前新闻没有被设置为已读,就要把数据库已读字更改
					if(!news.getIsReaded()){
						this.dalHelper.markNewsInfoAsReaded(news.getNewsId());
					}
					
					
					startActivity(intent);
					
				}
				
		
		
	}


