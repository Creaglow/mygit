package com.oracle.myblog.cache;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import com.oracle.myblog.R;
import com.oracle.myblog.util.NetHelper;  

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message; 

/**
 * 图片缓存
 * 
 * @author Administrator
 *
 */
public class AsyncImageLoader {

	private HashMap<String, SoftReference<Drawable>> imageCache;
	private Context curContext;

	/**
	 * 图片缓存类
	 * 
	 * @param curContext
	 */
	public AsyncImageLoader(Context curContext) {
		super();
		this.curContext = curContext;
		this.imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	/**
	 * 直接下载图片
	 * 
	 * @param imgType
	 * @param imageUrl
	 */
	public void loadDrawable(final ImageCacher.EnumImageType imageType,
			final String imageUrl) {
		final String folder = ImageCacher.GetImageFolder(imageType);

		new Thread() {
			@Override
			public void run() {
				NetHelper.loadImageFromUrlWidthStore(folder, imageUrl);
			}
		}.start();

	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String tag);
	}
	
	/**
	 * 将下载到本地并保存
	 * 
	 * @param imgType
	 * @param tag
	 * @param imageCallback
	 * @return
	 */
	public Drawable loadDrawable(final ImageCacher.EnumImageType imgType,
			final String tag, final ImageCallback imageCallback) {
		Drawable sampleDrawable = curContext.getResources().getDrawable(
				R.drawable.sample_face);
		if (tag.trim().equals("")) {
			return sampleDrawable;
		}
		String[] twoParts = tag.split("\\|", 2);
		final String imageUrl = twoParts[0];
		final String folder = ImageCacher.GetImageFolder(imgType);
		String outFilename = folder
				+ imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	 
		File file = new File(outFilename);
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		} else if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(outFilename);
			Drawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}

		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, tag);
			}
		};

		new Thread() {
			public void run() { 
				Drawable drawable = NetHelper.loadImageFromUrlWidthStore(folder,
						imageUrl);
				if (drawable == null) {
					drawable = NetHelper.loadImageFromUrl(imageUrl);
					if (drawable != null) {
						imageCache.put(imageUrl, new SoftReference<Drawable>(
								drawable));
					}
				} else
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return sampleDrawable;
	}
}
