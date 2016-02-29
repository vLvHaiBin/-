package com.example.fastNews.bean;

import java.util.ArrayList;

public class caldanInfo {

	public class mResult {
		public ArrayList<caidanDetail> data;
	}

	public class caidanDetail {
		
		public String id;
		public String title;
		public String tags;
		public String imtro;
		public String ingredients;
		public String burden;
		public ArrayList<mSteps> steps;
	}

	public class mSteps {
		public String img;
		public String step;
	}
}
