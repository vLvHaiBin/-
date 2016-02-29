package com.example.mobilesafe.Dao;

import java.util.ArrayList;

import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.bean.Bean_blacklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

public  class Dao_blacklist {
	
private static MyopenHelper helper;
private static String TABLE = "blacklist";
private static String NAME = "name";
private static String NUMBER = "number";
private static String STATUS = "status";
public  static ArrayList<Bean_blacklist> blacklists = null;
public  static ArrayList<Bean_blacklist> pagenumber;



public Dao_blacklist(Context context) {
	helper = new MyopenHelper(context,"blacklist.db",null, 1);
	blacklists = new ArrayList<Bean_blacklist>();
	pagenumber = new ArrayList<Bean_blacklist>();
}

	/**
	 * 插入内容
	 */
	public static void doInsert(Context context,String name,String number,String status){
		SQLiteDatabase dao = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, name);
		values.put(NUMBER, number);
		values.put(STATUS,status);
		dao.insert(TABLE, null, values);
		dao.close();
	}
	
	
	/**
	 * @param context
	 * @param start 从第几条开始查询
	 * @param shumu 查询多少条数据
	 * @return
	 */
	public static ArrayList<Bean_blacklist> doqueryAll(Context context,String start,String shumu){
		blacklists.clear();
		
		SQLiteDatabase dao = helper.getReadableDatabase();
		
		Cursor query = dao.query(TABLE, new String[]{NAME,NUMBER,STATUS}, null, null, null, null, null, start+","+shumu);
		while(query.moveToNext()){
			String name = query.getString(0);
			String number = query.getString(1);
			String status = query.getString(2);
			Bean_blacklist bean_blacklist = new Bean_blacklist(name, number, status);
			blacklists.add(bean_blacklist);
		}
		query.close();
		dao.close();
		return blacklists;
	}
	
	
	
	public static ArrayList<Bean_blacklist> doqueryAll2(Context context){
		blacklists.clear();
		
		SQLiteDatabase dao = helper.getReadableDatabase();
		
		Cursor query = dao.query(TABLE, new String[]{NAME,NUMBER,STATUS}, null, null, null, null, null, null);
		while(query.moveToNext()){
			String name = query.getString(0);
			String number = query.getString(1);
			String status = query.getString(2);
			Bean_blacklist bean_blacklist = new Bean_blacklist(name, number, status);
			blacklists.add(bean_blacklist);
		}
		query.close();
		dao.close();
		return blacklists;
	}
	
	/**
	 * @param number
	 * 删除内容
	 */
	public static void doDelete(String number){
		
		SQLiteDatabase dao = helper.getReadableDatabase();
		dao.delete(TABLE, NUMBER, new String[]{number});
		dao.close();
	}
	
	public static int getcallNumber(){
			SQLiteDatabase dao = helper.getReadableDatabase();
			Cursor query = dao.query(TABLE, new String[]{NUMBER}, null, null, null, null, null,null);
			while(query.moveToNext()){
				String number = query.getString(0);
				Bean_blacklist bean_blacklist = new Bean_blacklist("kong", number, "kong");
				pagenumber.add(bean_blacklist);
			}
		int page = pagenumber.size();
			query.close();
			dao.close();
		return page;
			
	}
	
	
	class MyopenHelper extends SQLiteOpenHelper{

		public MyopenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table blacklist(_id integer primary key "
					+ "autoincrement,name char(10),number char(10),status char(10))");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
	}
}
