package com.oracle.myblog.xml;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.entity.News;
import com.oracle.myblog.xml.blog.BlogContentXmlParser;
import com.oracle.myblog.xml.blog.BlogListXmlParser;
import com.oracle.myblog.xml.blog.NewsContentXmlParser;
import com.oracle.myblog.xml.blog.NewsListXmlParser;

public class NewsXmlParser {	
	/**
	 * 转换xml信息List集合中
	 * 
	 * @param dataString
	 * @return
	 */
	public static ArrayList<News> parseNewsString(String dataString) {
		
		ArrayList<News> arr = new ArrayList<News>();

		SAXParserFactory saxFactory = SAXParserFactory.newInstance();

		try {

			XMLReader xmlReader = saxFactory.newSAXParser().getXMLReader();

			// News xml解析器
			NewsListXmlParser handler = new NewsListXmlParser(arr);
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));

			arr = handler.getNewsList();

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
	public static String parseNewsContentString(String dataString) {
		
		 String htmlContext="";

		SAXParserFactory saxFactory = SAXParserFactory.newInstance();

		try {

			XMLReader xmlReader = saxFactory.newSAXParser().getXMLReader();

			// News xml解析器
			NewsContentXmlParser handler = new NewsContentXmlParser();
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));

			htmlContext = handler.getNewsContent();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlContext;
	}
	 


}

