package com.oracle.myblog.ui;

import com.oracle.myblog.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author Administrator
 *
 */
public class MoreActivity extends Fragment {

private View  currentView; //
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 
		 currentView=inflater.inflate(R.layout.activity_more, container,false);
		
		return currentView;
	}
}
