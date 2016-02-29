package com.example.mobilesafe.activity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.server.server_show_widget;
import com.example.mobilesafe.widght.MyWidget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;

public class splashActivity extends Activity {

	private InputStream open;
	private FileOutputStream outputStream;
	private FileOutputStream outputStream1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_activity);

		initData();
	}

	public void initData() {
		startService(new Intent(this, server_show_widget.class));
		copyDB("address.db");
		copyDB("antivirus.db");
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				Intent intent = new Intent(splashActivity.this, menu_activity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		};
		timer.schedule(task, 2000);
	}

	/**
	 * �������ݿ�
	 */
	private void copyDB(final String dbname) {
		new Thread() {
			public void run() {
				try {
					File file = new File(getFilesDir(), dbname);
					if (file.exists() && file.length() > 0) {
						Log.i("SplashActivity", "���ݿ��Ǵ��ڵġ����追��");
						return;
					}
					InputStream is = getAssets().open(dbname);
					FileOutputStream fos = openFileOutput(dbname, MODE_PRIVATE);
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

	private void createShortcut() {

		Intent intent = new Intent();

		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		// �������Ϊtrue��ʾ���Դ����ظ��Ŀ�ݷ�ʽ
		intent.putExtra("duplicate", false);

		/**
		 * 1 ��ʲô���� 2 ���ʲô���� 3�㳤��ʲô����
		 */
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,
				BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "�����ֻ���ʿ");
		// ��ʲô����
		/**
		 * ����ط�����ʹ����ʾ��ͼ ����ʹ����ʽ��ͼ
		 */
		Intent shortcut_intent = new Intent();

		shortcut_intent.setAction("menu.activity");

		shortcut_intent.addCategory("android.intent.category.DEFAULT");

		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcut_intent);

		sendBroadcast(intent);

	}
}
