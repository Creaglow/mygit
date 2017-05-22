package com.oracle.myblog.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * 文件操作工具类
 * 
 * @author Administrator
 *
 */
public class FileUtil {

	/**
	 * 创建文件夹
	 * 
	 * @param dirName
	 *            文件夹名称
	 */
	public static void makeDir(String dirName) { 
		File descDir = new File(dirName);
		if (!descDir.exists()) {
			descDir.mkdirs();
		}

	}

	/**
	 * 判断是否有内存卡
	 * 
	 * @return
	 */
	public static boolean hasSDCard() {
		return Environment.getExternalStorageDirectory().equals(
				Environment.MEDIA_MOUNTED);

	}

}
