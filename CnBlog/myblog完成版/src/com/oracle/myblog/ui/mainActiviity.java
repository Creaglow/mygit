package com.oracle.myblog.ui;

import com.oracle.myblog.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class mainActiviity extends Activity {
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.text);
	        
	        Handler handler = new Handler(); 

	        Runnable updateThread = new Runnable(){  
	                	public void run(){  
	                		Intent intent = new Intent(); 
	                        intent.setClass(mainActiviity.this,MainActivity.class);
	                        startActivity(intent);
	                        finish();

	                        }    
	            
	                	};
	        handler.postDelayed(updateThread, 5000);
	 }
}
