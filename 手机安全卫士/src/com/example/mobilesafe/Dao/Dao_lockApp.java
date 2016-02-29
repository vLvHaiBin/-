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

public  class Dao_lockApp {
	
private static MyopenHelper helper;
private static String TABLE = "lockapp";
private static String NAME = "packageName";




public Dao_lockApp(Context context) {
	helper = new MyopenHelper(context,"lockapp.db",null, 1);
	
}

	/**
	 * 插入内容
	 */
	public static void doInsert(String name){
		SQLiteDatabase dao = helper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, name);
		dao.insert(TABLE, null, values);
		dao.close();
	}
	
	
	/**
	 * @param context
	 * @param start 从第几条开始查询
	 * @param shumu 查询多少条数据
	 * @return
	 */
	public static String doqueryislock(String packagename){
		String islock = null;
		
		SQLiteDatabase dao = helper.getReadableDatabase();
		
		Cursor query = dao.query(TABLE, new String[]{"_id"},"packagename=?", new String[]{packagename}, null, null, null, null);
		while(query.moveToNext()){
			islock= query.getString(0);
			if(islock == null){
				return null;
			}
		}
		query.close();
		dao.close();
		return islock;
	}
	
	
	
	
	/**
	 * @param number
	 * 删除内容
	 */
	public static void doDelete(String packagename){
		
		SQLiteDatabase dao = helper.getReadableDatabase();
		dao.delete(TABLE, "packagename=?", new String[]{packagename});
		dao.close();
	}
	
	
	
	class MyopenHelper extends SQLiteOpenHelper{

		public MyopenHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table lockapp(_id integer primary key "
					+ "autoincrement,packagename char(10))");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
	}
}
