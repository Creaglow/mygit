package com.oracle.myblog.entity;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable{
	 
		private int newsId;//
		private String newsTitle;  //标题//
		
		
		
		private String newsContent; //内容//
		private Date published;//发表时间//
		private Date updateTime;  //更新时间//
		
		private int viewNum;     //浏览次数//
		private int commentNum; //评论次数//
		private int diggsNum;   //推荐次数//
		private String summary;  //简介//
	
		private String newsUrl; //博客地址 //
		private boolean isReaded; //是否已读//
		
		
		
		
		public int getNewsId() {
			return newsId;
		}
		public void setNewsId(int newsId) {
			this.newsId = newsId;
		}
		public String getNewsTitle() {
			return newsTitle;
		}
		public void setNewsTitle(String newsTitle) {
			this.newsTitle = newsTitle;
		}
	



		public String getNewsContent() {
			return newsContent;
		}
		public void setNewsContent(String newsContent) {
			this.newsContent = newsContent;
		}
		public Date getPublished() {
			return published;
		}
		public void setPublished(Date published) {
			this.published = published;
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

		public String getNewsUrl() {
			return newsUrl;
		}
		public void setNewsUrl(String newsUrl) {
			this.newsUrl = newsUrl;
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
			result = prime * result + newsId;
			return result;
		}
		
		
		//根据主键判断两个news对象是否相等
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			News other = (News) obj;
			if (newsId != other.newsId)
				return false;
			return true;
		}
		
		
		
		
	}
