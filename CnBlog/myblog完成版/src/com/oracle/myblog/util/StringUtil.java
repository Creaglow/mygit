package com.oracle.myblog.util;

public class StringUtil {

	 
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null){
			return true;
		}
		
		return str.trim().length()==0;
		
		
	}
}
