package com.oracle.myblog.xml.blog;

import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//ctrl+shift+o
/**
 * 博客内容解析器
 * 
 * @author Administrator
 * 
 */
public class NewsContentXmlParser extends DefaultHandler {

	final String ENTRY_TAG = "NewsBody"; // 主标识
	final String Content_TAG = "Content"; // 内容

	private boolean isStartParse; // 开始解析
	private StringBuffer currentDataBuilder; // 当前取得的信息

	String newsContent = ""; // 存放新闻内容

	/**
	 * 得到博客内容
	 * 
	 * @return
	 */
	public String getNewsContent() {
		return newsContent;
	}

	public NewsContentXmlParser() {

	}

	/**
	 * 文档开始时触发
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		currentDataBuilder = new StringBuffer();
	}

	/**
	 * 解析XML数据
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		// 每个对象都是以 entry 开头
		if (localName.equalsIgnoreCase(ENTRY_TAG)) {
			newsContent = "";
			isStartParse = true;
		}

	}

	/**
	 * 读取元素内容
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);

		currentDataBuilder.append(ch, start, length);
	}

	/**
	 * 元素结束时触发
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
          
		if (isStartParse) { 
			String chars = currentDataBuilder.toString();

			// 博客内容
			if (localName.equalsIgnoreCase(Content_TAG)) {
				chars = StringEscapeUtils.unescapeHtml(chars);// 进行编码处理，避免出现&gt;这种html
				newsContent = chars;
			}
		}

		// 清空字符串内容，给下一个标签使用
		currentDataBuilder.setLength(0);
	}

}
