package com.example.mobilesafe.Dao;


import com.example.mobilesafe.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * @author like
 *归属地查询
 */
public class AddressDAO {
	private static  final String PATH = "data/data/com.example.mobilesafe/files/address.db";
	private static String location = "查询失败";
	private static String id;
	private static Cursor query2 = null;
	public static String getAddress(String number){
		if(number.length() > 6 ){
			id = number.substring(0,7);
		}else {
			id = number;
		}
			
		
		String[] columns = new String[]{"outkey"};
		SQLiteDatabase sq =  SQLiteDatabase.openDatabase(PATH, null, 
				SQLiteDatabase.OPEN_READONLY);
		Cursor query = sq.query("data1", columns, "id=?", new String[]{id}, null, null, null, null);
		try {
			if (query.moveToNext()) {
				String outkey = query.getString(0);
				if(outkey == null){
					return "查询失败";
				}
				String[] id2 = new String[] { "location" };
				query2 = sq.query("data2", id2, "id=?", new String[] { outkey }, null, null, null, null);
				if (query2.moveToNext()) {
					location = query2.getString(0);
					
				}
			} 
		} finally {
			query.close();
			if(query2 != null){
				query2.close();
			}
		
		}
		return location;
	}
	
	/**
	 * @param view
	 * @param text
	 * @param context
	 * 动画效果
	 */
	public static void animation(View view,String text,Context context) {
		
		String ss = text.trim();//去除空格
		
		if(TextUtils.isEmpty(ss)){
			Animation animation = AnimationUtils.loadAnimation(context,R.anim.translate_edit);
			animation.setFillAfter(true);
			view.startAnimation(animation);
		}
	}
}
