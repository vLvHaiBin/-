package com.example.fastNews.bean;

import java.util.ArrayList;

/**
 * @author like Âþ»­Bean
 */
public class ComicBean {

	public String reason;
	public mResult result;

	public class mResult {
		public ArrayList<mBookList> bookList;
	}

	public class mBookList {
		public String name;
		public String type;
		public String area;
		public String des;
		public boolean finish;
		public String lastUpdate;
		public String coverImg;

	}
}
