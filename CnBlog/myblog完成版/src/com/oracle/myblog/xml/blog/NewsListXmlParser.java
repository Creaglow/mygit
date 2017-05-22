package com.oracle.myblog.xml.blog;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.entity.News;
import com.oracle.myblog.util.DateUtil;

public class NewsListXmlParser extends DefaultHandler {
	final String ENTRY_TAG = "entry";// 主标记
	final String ENTRY_ID_TAG = "id";// 编号标记
	final String ENTRY_TITLE_TAG = "title";// 标题标记
	final String ENTRY_SUMMARY_TAG = "summary";// 简介标记
	final String ENTRY_PUBLISHED_TAG = "published";// 发表时间标记
	final String ENTRY_UPDATED_TAG = "updated";// 更新时间标记

	final String ENTRY_LINK_TAG = "link";// 实际链接地址
	final String ENTRY_DIGG_TAG = "diggs";// 推荐次数
	final String ENTRY_VIEW_TAG = "views";// 查看次数
	final String ENTRY_COMMENTS_TAG = "comments";// 评论次数

	final String ENTRY_URL_TAG = "link";// 实际网址标签
	final String ENTRY_URL_ATTRIBUTE_TAG = "href";// 网址属性标签

	private ArrayList<News> listNews;
	private News entity; // 存放单个对象
	private boolean isStartParse; // 开始解析
	private StringBuffer currentDataBuilder; // 当前取得的信息

	public NewsListXmlParser() {

	}

	public NewsListXmlParser(ArrayList<News> listNews) {
		this.listNews = listNews;
	}

	/**
	 * 得到博客住处
	 * 
	 * @return
	 */
	public ArrayList<News> getNewsList() {
		return listNews;
	}

	/**
	 * 文档开始时触发
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		listNews = new ArrayList<News>();
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
			entity = new News();
			isStartParse = true;
		}

		// 实际网址标签
		if (isStartParse && localName.equalsIgnoreCase(ENTRY_URL_TAG)) {
			entity.setNewsUrl(attributes.getValue(ENTRY_URL_ATTRIBUTE_TAG));
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

			// 标题
			if (localName.equalsIgnoreCase(ENTRY_TITLE_TAG)) {
				chars = StringEscapeUtils.unescapeHtml(chars);// 进行编码处理，避免出现&gt;这种html
				entity.setNewsTitle(chars);

			} else if (localName.equalsIgnoreCase(ENTRY_SUMMARY_TAG)) {
				// 摘要
				chars = StringEscapeUtils.unescapeHtml(chars);// 进行编码处理，避免出现&gt;这种html
				entity.setSummary(chars);
			} else if (localName.equalsIgnoreCase(ENTRY_ID_TAG)) {// 编号
				int id = Integer.parseInt(chars);
				entity.setNewsId(id);

			} else if (localName.equalsIgnoreCase(ENTRY_DIGG_TAG)) {// 推荐次数
				entity.setDiggsNum(Integer.parseInt(chars));
			} else if (localName.equalsIgnoreCase(ENTRY_VIEW_TAG)) {// 查看次数
				entity.setViewNum(Integer.parseInt(chars));
			} else if (localName.equalsIgnoreCase(ENTRY_COMMENTS_TAG)) {// 评论次数
				entity.setCommentNum(Integer.parseInt(chars));

			} else if (localName.equalsIgnoreCase(ENTRY_PUBLISHED_TAG)) {// 发布时间
				Date addTime = DateUtil.parseUTCDate(chars);
				entity.setPublished(addTime);
			} else if (localName.equalsIgnoreCase(ENTRY_UPDATED_TAG)) {// 修改时间
				Date updateTime = DateUtil.parseUTCDate(chars);
				entity.setUpdateTime(updateTime);
			} else if (localName.equalsIgnoreCase(ENTRY_TAG)) {// 截止
				listNews.add(entity);
				isStartParse = false;
			}

		}

		// 清空字符串内容，给下一个标签使用
		currentDataBuilder.setLength(0);
	}

}
