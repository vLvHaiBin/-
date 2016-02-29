package com.example.fastNews.bean;

import java.util.ArrayList;

public class joke {
	
	public ArrayList<jokedetail> detail;

	public ArrayList<jokedetail> getDetail() {
		return detail;
	}

	public void setDetail(ArrayList<jokedetail> detail) {
		this.detail = detail;
	}

	public class jokedetail {
		
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getPicUrl() {
			return picUrl;
		}
		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}
		public String author;
		public String content;
		public String picUrl;
	}
}
