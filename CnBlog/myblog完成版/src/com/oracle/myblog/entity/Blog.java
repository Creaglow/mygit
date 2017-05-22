package com.oracle.myblog.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * 
 * @author Administrator
 * 
 */
public class Blog implements Serializable {

	private int blogId;
	private String blogTitle; // 标题
	private String author;// 作者博客名
	private String userName;// 用户名
	private String authorUrl; // 作者博客地址
	private String blogContent; // 内容
	private Date addTime; // 发布时间
	private Date updateTime; // 更新时间
	private int viewNum; // 浏览次数
	private int commentNum; // 评论次数
	private int diggsNum; // 推荐次数
	private String summary; // 简介
	private String avator; // 头像
	private String blogUrl; // 博客地址
	private boolean isReaded; // 是否已读

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public String getBlogContent() {
		return blogContent;
	}

	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getViewNum() {
		return viewNum;
	}

	public void setViewNum(int viewNum) {
		this.viewNum = viewNum;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public int getDiggsNum() {
		return diggsNum;
	}

	public void setDiggsNum(int diggsNum) {
		this.diggsNum = diggsNum;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAvator() {
		return avator;
	}

	public void setAvator(String avator) {
		this.avator = avator;
	}

	public String getBlogUrl() {
		return blogUrl;
	}

	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}

	public boolean getIsReaded() {
		return isReaded;
	}

	public void setIsReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blogId;
		return result;
	}

	// 根据主键判断两个blog对象是否相等
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Blog other = (Blog) obj;
		if (blogId != other.blogId)
			return false;
		return true;
	}

}
