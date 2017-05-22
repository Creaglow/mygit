package com.oracle.myblog.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.oracle.myblog.R;

/**
 * 主界面
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends FragmentActivity {

	// ctrl+/ 代码提示 ctrl+shift+f 整理代码缩近 

	private FragmentTabHost mTabhost_menu;
 

	// 加载布局
	private LayoutInflater mLayoutInflater;

	// 图片资源
	private int[] mImageViewArray = { R.drawable.icon_1_n, R.drawable.icon_7_n,
			R.drawable.icon_8_n, R.drawable.icon_4_n, R.drawable.icon_5_n };

	//图片资源对应的文字
	private String[] mTextArray={"博客","新闻","收藏","搜索","更多"};
	
	//每个图片对应的界面
	private Class[] mFramentArray={BlogActivity.class,NewsActivity.class,FavoriteActivity.class,SearchActivity.class,MoreActivity.class};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}
	
	//查询资源
	private void initView(){
		this.mLayoutInflater=LayoutInflater.from(this);
		
		//TabHost 
		this.mTabhost_menu=(FragmentTabHost) findViewById(R.id.tabhost_menu);
		//tab之间切换将在TabContent所关联的FrameLayout区域中显示各自板块的内容
		this.mTabhost_menu.setup(this, getSupportFragmentManager(),R.id.framelayout_content); 
		
		int len=mImageViewArray.length; //底部菜单个数
		
		for(int i=0;i<len;i++){
			//为每一个TabHost按钮设置文字和图片
			TabSpec spec=this.mTabhost_menu.newTabSpec(this.mTextArray[i])
					.setIndicator(newTabView(i));
			
			//追加到mTabhost_menu 里面去
			//addTab(spec,界面,null)
			this.mTabhost_menu.addTab(spec,mFramentArray[i],null);
			
		}
		
	}
	
	
	//创建视图
	private View newTabView(int index){
		//动态加载  layout_tab_item.xml  界面到内存当中
		View v=this.mLayoutInflater.inflate(R.layout.layout_tab_item, null);
		
		//设定图片
		ImageView imgView=(ImageView) v.findViewById(R.id.tab_img);
		imgView.setImageResource(mImageViewArray[index]);
		
		//设定文字
		TextView  tv=(TextView) v.findViewById(R.id.tab_text);
		tv.setText(mTextArray[index]);
		
		return v;
	}
	
	

}
