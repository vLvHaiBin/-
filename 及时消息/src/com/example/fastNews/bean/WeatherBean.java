package com.example.fastNews.bean;

import java.util.ArrayList;

public class WeatherBean {
	
	public String desc;
	public  mData data;
	
	public class mData { 
		public String wendu;
		public String ganmao;
		public ArrayList<mForecast> forecast;
		public mYesterday yesterday;
		public String city;
	}
	
	public class mForecast {
		public String fengxiang;
		public String fengli;
		public String high;
		public String type;
		public String low;
		public String date;
		
	}
	
	public class mYesterday {
		public String fl;
		public String fx;
		public String hight;
		public String type;
		public String low;
		public String date;
	}
}
