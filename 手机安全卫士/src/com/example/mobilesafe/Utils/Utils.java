package com.example.mobilesafe.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.xmlpull.v1.XmlSerializer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {

	private static boolean islog = true;
	static final Handler handler = new Handler();

	public static void sharprefrence_save(Context context, String name, String value) {
		SharedPreferences preferences = context.getSharedPreferences("config", context.MODE_PRIVATE);
		preferences.edit().putString(name, value).commit();
	}

	public static String sharprefrence_get(Context context, String name, String value) {
		SharedPreferences preferences = context.getSharedPreferences("config", context.MODE_PRIVATE);
		String getText = preferences.getString(name, value);
		return getText;
	}

	public static void sharprefrence_saveInt(Context context, String name, int value) {
		SharedPreferences preferences = context.getSharedPreferences("config", context.MODE_PRIVATE);
		preferences.edit().putInt(name, value).commit();
	}

	public static int sharprefrence_getInt(Context context, String name, int value) {
		SharedPreferences preferences = context.getSharedPreferences("config", context.MODE_PRIVATE);
		int getText = preferences.getInt(name, value);
		return getText;
	}

	public static void logError(Context context, String value) {
		if (islog) {
			Log.e("xians", value);
		}
	}

	public static void toast(Context context, String value) {
		Toast.makeText(context, value, 0).show();
	}

	/**
	 * MD5加密算法
	 * 
	 * @param password
	 * @return
	 */
	public static String MD5_encode(String password) {
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");// 获取MD5算法对象
			byte[] digest = instance.digest(password.getBytes());// 对字符串加密,返回字节数组

			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				int i = b & 0xff;// 获取字节的低八位有效值
				String hexString = Integer.toHexString(i);// 将整数转为16进制

				if (hexString.length() < 2) {
					hexString = "0" + hexString;// 如果是1位的话,补0
				}

				sb.append(hexString);
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// 没有该算法时,抛出异常, 不会走到这里
		}

		return "";
	}

	/**
	 * @param context
	 * @param content
	 *            自定义toast
	 */
	public static void showToast(final Context context, String content) {

		final WindowManager manager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		final int width = manager.getDefaultDisplay().getWidth();
		final int height = manager.getDefaultDisplay().getHeight();
		final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		/*
		 * params.windowAnimations =
		 * com.android.internal.R.style.Animation_Toast;
		 */
		params.type = WindowManager.LayoutParams.TYPE_PHONE;// 不同的属性在界面上优先级和显示效果不一样
		params.setTitle("Phone");

		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		/* | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE */;
		params.gravity = Gravity.LEFT + Gravity.TOP;// 设置toast的位置
		int x = Utils.sharprefrence_getInt(context, "lastX", 0);
		int y = Utils.sharprefrence_getInt(context, "lastY", 0);
		if (x != 0 && y != 0) {// 设置toast的位置
			params.x = x;
			params.y = y;
		}

		final TextView tv = new TextView(context);
		tv.setText(content);
		tv.setTextSize(30);
		manager.addView(tv, params);
		tv.setOnTouchListener(new OnTouchListener() {

			private int startX;
			private int startY;

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
			 * android.view.MotionEvent) toast的移动
			 */
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;

				case MotionEvent.ACTION_MOVE:
					int moveX = (int) event.getRawX();
					int moveY = (int) event.getRawY();

					int endX = moveX - startX;
					int endY = moveY - startY;

					params.x += endX;
					params.y += endY;
					if (params.x > width - tv.getWidth()) {
						params.x = width - tv.getWidth();
					} else if (params.x < 0) {
						params.x = 0;
					}
					if (params.y > height - tv.getHeight() - 70) {
						params.y = height - tv.getHeight() - 70;
					} else if (params.y < 0) {
						params.y = 0;
					}
					startX = moveX;
					startY = moveY;
					manager.updateViewLayout(tv, params);// 更新位置
					break;

				case MotionEvent.ACTION_UP:
					Utils.sharprefrence_saveInt(context, "lastX", params.x);
					Utils.sharprefrence_saveInt(context, "lastY", params.y);
					break;

				}
				return true;
			}
		});
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				manager.removeView(tv);
			}
		}, 5000);
	}

	/**
	 * @param context
	 *            屏幕密度，px / 密度 = dp; 将dp装换为px
	 */
	public static int getscreendensity(Context context, int dp) {
		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (dp * density);
		return px;
	}

	/**
	 * @return 判断sd卡是否可用
	 */
	public static boolean ExternalStorageStateCanUse() {

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @param context
	 *            获取所有短信信息
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	public static void getAllSMS(Context context) throws IllegalArgumentException, IllegalStateException, IOException {
		boolean isUse = Utils.ExternalStorageStateCanUse();

		int current = 1;
		File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "messagebackups.xml");
		FileOutputStream os = null;
		os = new FileOutputStream(file);
		XmlSerializer xmlSerializer = Xml.newSerializer();
		xmlSerializer.setOutput(os, "utf-8");

		Uri uri = Uri.parse("content://sms/");
		ContentResolver contentResolver = context.getContentResolver();
		Cursor query = contentResolver.query(uri, new String[] { "address", "date", "type", "body" }, null, null, null);
		xmlSerializer.startDocument("utf-8", true);
		while (query.moveToNext()) {
			// 开始
			xmlSerializer.startTag(null, "smss");

			xmlSerializer.startTag(null, "address");
			xmlSerializer.text(query.getString(0));
			xmlSerializer.endTag(null, "address");

			xmlSerializer.startTag(null, "date");
			xmlSerializer.text(query.getString(1));
			xmlSerializer.endTag(null, "date");

			xmlSerializer.startTag(null, "type");
			xmlSerializer.text(query.getString(2));
			xmlSerializer.endTag(null, "type");

			xmlSerializer.startTag(null, "body");
			xmlSerializer.text(query.getString(3));
			xmlSerializer.endTag(null, "body");
			// 结束
			xmlSerializer.endTag(null, "smss");
			current++;
		}
		xmlSerializer.endDocument();
		query.close();
		os.close();

	}

	// 文件复制到data/data/com..../files/..
	private void copyDB(final String dbname, final Context context) {
		new Thread() {
			public void run() {
				try {
					File file = new File(context.getFilesDir(), dbname);
					if (file.exists() && file.length() > 0) {
						Log.i("SplashActivity", "数据库是存在的。无需拷贝");
						return;
					}
					InputStream is = context.getAssets().open(dbname);
					FileOutputStream fos = context.openFileOutput(dbname, context.MODE_PRIVATE);
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					is.close();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * @param context
	 * @return
	 * 获取版本号
	 */
	public static int getVersionCode(Context context) {
		
		
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);// 获取包的信息

			int versionCode = packageInfo.versionCode;
			return versionCode;
		} catch (NameNotFoundException e) {
			// 没有找到包名的时候会走此异常
			e.printStackTrace();
		}

		return -1;
	}
}
