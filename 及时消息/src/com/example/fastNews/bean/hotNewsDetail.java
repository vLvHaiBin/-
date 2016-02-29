package com.example.fastNews.bean;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class hotNewsDetail {

	public ArrayList<mResult> result;

	public class mResult {
		public String title;
		public String content;
		public String full_title;
		public String pdate;
		public String src;
		public String url;
		public String pdate_src;
		public String img;
	}
}
