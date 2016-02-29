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
 * @author like 归属地查询
 */
public class virusDAO {
	private static final String PATH = "data/data/com.example.mobilesafe/files/antivirus.db";
	private static  String virus = null;

	public static String getVirus(String md5) {

		String[] columns = new String[] {"desc" };
		SQLiteDatabase sq = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
		Cursor query = sq.query("datable", columns, "md5=?", new String[]{md5}, null, null, null, null);
		try {
			if (query.moveToNext()) {
				virus = query.getString(0);
				if(md5 ==null){
					return null;
				}
			}
		} finally {
			query.close();
			if (query != null) {
				query.close();
			}

		}
		return virus;
	}

	/**
	 * @param view
	 * @param text
	 * @param context
	 *            动画效果
	 */
	public static void animation(View view, String text, Context context) {

		String ss = text.trim();// 去除空格

		if (TextUtils.isEmpty(ss)) {
			Animation animation = AnimationUtils.loadAnimation(context, R.anim.translate_edit);
			animation.setFillAfter(true);
			view.startAnimation(animation);
		}
	}
}
