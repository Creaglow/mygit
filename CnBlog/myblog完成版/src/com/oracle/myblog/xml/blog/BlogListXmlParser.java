package com.oracle.myblog.xml.blog;

import java.util.ArrayList;
import java.util.Date;
 
import org.apache.commons.lang.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.oracle.myblog.entity.Blog;
import com.oracle.myblog.util.DateUtil;

 

/**
 * Blog返回xml解析器
 * 
 * @author Administrator
 *
 */
public class BlogListXmlParser extends DefaultHandler {
	final String ENTRY_TAG = "entry";// 主标记
	final String ENTRY_ID_TAG = "id";// 编号标记
	final String ENTRY_TITLE_TAG = "title";// 标题标记
	final String ENTRY_SUMMARY_TAG = "summary";// 简介标记
	final String ENTRY_PUBLISHED_TAG = "published";// 发表时间标记
	final String ENTRY_UPDATED_TAG = "updated";// 更新时间标记
	final String ENTRY_AUTHOR_NAME_TAG = "name";// 发表者名称
	final String ENTRY_USER_NAME_TAG = "blogapp";// 作者用户名
	final String ENTRY_AUTHOR_URL_TAG = "uri";// 发表者主页
	final String ENTRY_LINK_TAG = "link";// 实际链接地址
	final String ENTRY_DIGG_TAG = "diggs";// 推荐次数
	final String ENTRY_VIEW_TAG = "views";// 查看次数
	final String ENTRY_COMMENTS_TAG = "comments";// 评论次数
	final String ENTRY_AVATOR_TAG = "avatar";// 头像地址
	final String ENTRY_URL_TAG = "link";// 实际网址标签
	final String ENTRY_URL_ATTRIBUTE_TAG = "href";// 网址属性标签

	private ArrayList<Blog> listBlog;
	private Blog entity; // 存放单个对象
	private boolean isStartParse; // 开始解析
	private StringBuffer currentDataBuilder; // 当前取得的信息

	public BlogListXmlParser() {

	}

	public BlogListXmlParser(ArrayList<Blog> listBlog) {
		this.listBlog = listBlog;
	}

	/**
	 * 得到博客住处
	 * @return
	 */
	public ArrayList<Blog> getBlogList() {
		return listBlog;
	}

	/**
	 * 文档开始时触发
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		listBlog = new ArrayList<Blog>();
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
			entity = new Blog();
			isStartParse = true;
		}

		// 实际网址标签
		if (isStartParse && localName.equalsIgnoreCase(ENTRY_URL_TAG)) {
			entity.setBlogUrl(attributes.getValue(ENTRY_URL_ATTRIBUTE_TAG));
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
				entity.setBlogTitle(chars);

			} else if (localName.equalsIgnoreCase(ENTRY_SUMMARY_TAG)) {
				// 摘要
				chars = StringEscapeUtils.unescapeHtml(chars);// 进行编码处理，避免出现&gt;这种html
				entity.setSummary(chars);
			} else if (localName.equalsIgnoreCase(ENTRY_ID_TAG)) {// 编号
				int id = Integer.parseInt(chars);
				entity.setBlogId(id);
			} else if (localName.equalsIgnoreCase(ENTRY_PUBLISHED_TAG)) {// 发布时间
				Date addTime = DateUtil.parseUTCDate(chars);
				entity.setAddTime(addTime);
			} else if (localName.equalsIgnoreCase(ENTRY_UPDATED_TAG)) {// 修改时间
				Date updateTime = DateUtil.parseUTCDate(chars);
				entity.setUpdateTime(updateTime);
			} else if (localName.equalsIgnoreCase(ENTRY_AUTHOR_NAME_TAG)) {// 作者名称
                entity.setAuthor(chars);
			}else if (localName.equalsIgnoreCase(ENTRY_USER_NAME_TAG)) {// 作者用户名
				entity.setUserName(chars);
			}else if (localName.equalsIgnoreCase(ENTRY_DIGG_TAG)) {// 推荐次数
			    entity.setDiggsNum(Integer.parseInt(chars));	
			}else if (localName.equalsIgnoreCase(ENTRY_VIEW_TAG)) {// 查看次数
				entity.setViewNum(Integer.parseInt(chars));
			}else if (localName.equalsIgnoreCase(ENTRY_COMMENTS_TAG)) {// 评论次数
				entity.setCommentNum(Integer.parseInt(chars));
			}else if (localName.equalsIgnoreCase(ENTRY_AVATOR_TAG)) {// 用户头像
				entity.setAvator(chars);
			}else if (localName.equalsIgnoreCase(ENTRY_AUTHOR_URL_TAG)) {// 用户主页
				entity.setBlogUrl(chars);
			}else if (localName.equalsIgnoreCase(ENTRY_TAG)) {// 截止
				listBlog.add(entity);
				isStartParse=false;
			}
		}

		// 清空字符串内容，给下一个标签使用
		currentDataBuilder.setLength(0);
	}

}
