package com.oracle.myblog.xml;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.xml.blog.BlogContentXmlParser;
import com.oracle.myblog.xml.blog.BlogListXmlParser;

/**
 * 博客信息解析类
 * @author Administrator
 *
 */
public class BlogXmlParser {

	
	/**
	 * 转换xml信息List集合中
	 * 
	 * @param dataString
	 * @return
	 */
	public static ArrayList<Blog> parseBlogString(String dataString) {  //BlogDalHelper里用到这个方法
		
		ArrayList<Blog> arr = new ArrayList<Blog>();
		
		// 使用SAXParserFactory创建SAX解析工厂
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();

		try {
			// 通过解析器对象得到一个XML的读取器,saxFactory.newSAXParser():通过SAX解析工厂得到解析器对象
			XMLReader xmlReader = saxFactory.newSAXParser().getXMLReader();

			// Blog xml解析器
			BlogListXmlParser handler = new BlogListXmlParser(arr);
			// 设置读取器的事件处理器
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));

			arr = handler.getBlogList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	/**
	 * 转换xml信息博客住处
	 * 
	 * @param dataString
	 * @return
	 */
	public static String parseBlogContentString(String dataString) {
		
		 String htmlContext="";
		 
        // 使用SAXParserFactory创建SAX解析工厂
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();

		try {
			// 通过解析器对象得到一个XML的读取器,saxFactory.newSAXParser():通过SAX解析工厂得到解析器对象
			XMLReader xmlReader = saxFactory.newSAXParser().getXMLReader();

			// Blog xml解析器			
			BlogContentXmlParser handler = new BlogContentXmlParser();
			// 设置读取器的事件处理器
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));

			htmlContext = handler.getBlogContent();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlContext;
	}
	 

}
