package com.oracle.myblog.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

import com.oracle.myblog.core.ConfigUtil;
import com.oracle.myblog.util.FileUtil; 

import android.content.Context; 

public class ImageCacher {

	private Context context;

	public ImageCacher(Context context) {
		super();
		this.context = context;
	}

	public ImageCacher() {
		super();
	}

	/**
	 * 图片类型
	 * 
	 * @author Administrator
	 *
	 */
	public enum EnumImageType {
		/**
		 * 头像
		 */
		Avatar,
		/**
		 * 博客
		 */
		Blog,
		/**
		 * 新闻
		 */
		News, 
		/**
		 * 临时文件夹
		 */
		Temp
	}

	/**
	 * 得到图片地址文件夹
	 * 
	 * @param imageType
	 * @return
	 */
	public static String GetImageFolder(EnumImageType imageType) {

		String folder =   ConfigUtil.TEMP_IMAGES_LOCATION ;
		switch (imageType) {
		default:
		case Temp:
			folder += "temp/";
			break;
		case Avatar:
			folder += "avatar/";
			break;
		case Blog:
			folder += "blog/";
			break;
		case News:
			folder += "news/";
			break; 
		}
		return folder; 
	}

	//图片匹配地址
	static final Pattern patternImgSrc = Pattern
			.compile("<img(.+?)src=\"(.+?)\"(.+?)>");
	
	
	/**
	 * 得到html中的图片地址
	 * 
	 * @param html
	 * @return
	 */
	private static List<String> GetImagesList(String html) {
		List<String> listSrc = new ArrayList<String>();
		Matcher m = patternImgSrc.matcher(html);
		while (m.find()) {
			listSrc.add(m.group(2));
		} 
		return listSrc;
	}
	
	/**
	 * 得到新图片地址（本地路径）
	 * 
	 * @param imgType
	 * @param imageUrl
	 * @return
	 */
	private static String GetNewImgSrc(EnumImageType imgType, String imageUrl) {
		if (imageUrl.contains("?")) {// 截断?后的字符串，避免无效图片
			imageUrl = imageUrl.substring(0, imageUrl.indexOf("?"));
		}
		imageUrl = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

		String folder = GetImageFolder(imgType);

		return "file:///mnt" + folder + imageUrl;
	}
	
	
	/**
	 * 得到格式化后的html
	 * 
	 * @param imgType
	 * @param html
	 * @return
	 */
	public static String FormatLocalHtmlWithImg(EnumImageType imgType,
			String html) {
		List<String> listSrc = GetImagesList(html);
		for (String src : listSrc) {
			String newSrc = GetNewImgSrc(imgType, src);
			html = html.replace(src, newSrc);
		} 
		return html;
	}
	
}
