package com.oracle.myblog.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.oracle.myblog.core.ConfigUtil;

 

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

/**
 * 网络文件下载类
 * @author Administrator
 *
 */
public class NetHelper {

	/**
	 * 下载图片到本地
	 * @param folder
	 * @param url
	 * @return
	 */
	public static Drawable loadImageFromUrlWidthStore(String folder,String url){
		try {
			//注意url可能包含?的情况，需要在?前截断
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"));  // substring(int start,int end)
			}
			
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			String encodeFileName = URLEncoder.encode(fileName, ConfigUtil.ENCODE_TYPE);
			
			URL imageUrl=new URL(url.replace(fileName, encodeFileName));
			 //下载图片流
			byte[] data=readInputStream(imageUrl.openStream());
			Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
			
			String status=Environment.getExternalStorageState();
			if(status.equals(Environment.MEDIA_MOUNTED)){
				FileUtil.makeDir(folder);
				
				String outFileName=folder+fileName; //文件路径
				bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(outFileName));
				
				Bitmap bitmapCompress=BitmapFactory.decodeFile(outFileName);
				Drawable dw=new BitmapDrawable(bitmapCompress);
				return dw;
			} 
		} catch (Exception e) {
			Log.e("download_img_err", e.toString()); 
		} 
		return null;
	}
	
	/**
	 * 下载图片
	 * @param url
	 * @return
	 */
	public static Drawable loadImageFromUrl(String url){
		InputStream is=null;
		try {
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			String encodeFileName=URLEncoder.encode(fileName);
			
			URL imageUrl=new URL(url.replace(fileName,encodeFileName));
			is=(InputStream) imageUrl.getContent();
			
		} catch (Exception e) {
			Log.e("There", e.toString());
		}
		Drawable d=Drawable.createFromStream(is, "src");
		return d;
	}
	
	
	
	/**
	 * 读取输入流
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
	    byte[] buffer=new byte[4096];
	    int len=0;
	    
	    while( (len=inStream.read(buffer))!=-1){
	       outStream.write(buffer, 0, len);	
	    }
	    outStream.close();
	    inStream.close();
	
	    return outStream.toByteArray();
	}
	
	
	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean networkIsAvaliable(Context context){
		ConnectivityManager cManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    
		NetworkInfo info=cManager.getActiveNetworkInfo();
		if(info==null){
			return false;
		}
		
		//获取网络对象
        NetworkInfo[] netWork=cManager.getAllNetworkInfo();
        if(netWork!=null && netWork.length>0){
            int len=netWork.length;
            for(int i=0;i<len;i++){ 
            	Log.d("NetHelper", i+"==状态："+netWork[i].getState()+"==类型：=="+netWork[i].getTypeName());
  
            	if(netWork[i].getState()==NetworkInfo.State.CONNECTED){
            		return true;
            	} 
            }
        	
        }
		
		return false; 
	}
	
	/**
	 * 根据URL地址从网络上读取数据，并返回结果
	 * @param url
	 * @return
	 */
	public static String getContentFromUrl(String url){
		String result="";
		
		try {
			DefaultHttpClient client=new DefaultHttpClient();
			HttpUriRequest request=new HttpGet(url);
			HttpResponse response=client.execute(request);
			
			HttpEntity entity=response.getEntity();
			int status=response.getStatusLine().getStatusCode();
			//如果status==200 代表请求地址并成功返回数据
			if(status==HttpStatus.SC_OK){
				 result=EntityUtils.toString(entity);
				//Encode utf-8 to iso-8859-1
				// result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
			}
			client.getConnectionManager().shutdown();
		 
		} catch (Exception e) {
			Log.e("NetHelper", "___读取数据失败" + e.toString()+ "___"); 
		} 
		return result;
	}
	
	
	 /**
	  * 根据URL地址从网络上读取数据，并返回结果
	  * @param url
	  * @param contentType 编码格式，如UTF-8
	  * @return
	  */
	public static String getContentFromUrl(String url,String contentType){
		String result="";
		
		try {
			DefaultHttpClient client=new DefaultHttpClient();
			HttpUriRequest request=new HttpGet(url);
			HttpResponse response=client.execute(request);
			request.getParams().setParameter("Content-Type", contentType);
			
			HttpEntity entity=response.getEntity();
			int status=response.getStatusLine().getStatusCode();
			//如果status==200 代表请求地址并成功返回数据
			if(status==HttpStatus.SC_OK){
				 result=EntityUtils.toString(entity);
				//Encode utf-8 to iso-8859-1
				  result = new String(result.getBytes("ISO-8859-1"), contentType);
			}
			client.getConnectionManager().shutdown();
		 
		} catch (Exception e) {
			Log.e("NetHelper", "___读取数据失败" + e.toString()+ "___"); 
		} 
		return result;
	}
	
	
	/**
	 * 根据URL地址从网络上读取XML数据，并返回结果
	 * @param url
	 * @param contentType 编码格式，如UTF-8
	 * @return
	 */
	public static String getXmlContentFromUrl(String url,String contentType){
		return getContentFromUrl(url, contentType).replaceAll("\n|\t|\r", "");
	}
	
}
