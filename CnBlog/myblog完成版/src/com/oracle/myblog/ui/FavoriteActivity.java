package com.oracle.myblog.ui;

import com.oracle.myblog.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * �ҹ�ע���Ž���
 * @author Administrator
 *
 */
public class FavoriteActivity extends Fragment{

	
private View  currentView; //��ǰ����
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 
		 currentView=inflater.inflate(R.layout.activity_favorite, container,false);
		
		return currentView;
	}
	
}
