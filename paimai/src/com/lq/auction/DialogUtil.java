package com.lq.auction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;

public class DialogUtil {

	public static void showDialog(final Context ctx
			, String msg, boolean goHome) {
		//创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
		    .setMessage(msg).setCancelable(false);
		if (goHome) {
			builder.setPositiveButton("确定", new OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(ctx,AuctionClientActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ctx.startActivity(intent);
					
				}
				
			});
			
		} else {
			builder.setPositiveButton("确定", null);

		}
		
		builder.create().show();
	
		
	}
	//定义一个显示指定组件的对话框
	public static void showDialog(Context ctx, View view){
		new AlertDialog.Builder(ctx)
		    .setView(view).setCancelable(false)
		    .setPositiveButton("确定", null)
		    .create()
		    .show();
		
	}

}
