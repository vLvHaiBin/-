package com.example.fastNews.bean;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class healthdetailInfo {

	public mResult result;

	public class mResult {
		public ArrayList<resultInfo> data;
	}

	public class resultInfo {
		public String id;
		public String keywords;
		public String title;
		public String description;
		public String img;
		public String time;
	}
}
