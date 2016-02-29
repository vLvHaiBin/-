package com.example.mobilesafe.server;

import java.util.Timer;
import java.util.TimerTask;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.SystemInfoUtils;
import com.example.mobilesafe.widght.MyWidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.widget.RemoteViews;

public class server_show_widget extends Service {

	private AppWidgetManager widgetManager;
	private Timer timer;
	private TimerTask timerTask;

	@Override
	public IBinder onBind(Intent intent) {
		return null;

	}

	@Override
	public void onCreate() {
		super.onCreate();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				widgetManager = AppWidgetManager.getInstance(server_show_widget.this);//首先获取widget控件
				RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);//获取布局文件
				long availMem = SystemInfoUtils.getAvailMem(server_show_widget.this);

				/*
				 * 
				 * button的点击事件
				 * Intent intent = new Intent();
				// 发送一个隐式意图
				intent.setAction("com.itheima.mobileguard");
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
				views.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);*/

				views.setTextViewText(R.id.tv_surplus_ram,
						"可用内存:" + Formatter.formatFileSize(server_show_widget.this, availMem));//textview的设置
				//第一个参数表示上下文
				//第二个参数表示当前有哪一个广播进行去处理当前的桌面小控件
				ComponentName provider = new ComponentName(getApplicationContext(), MyWidget.class);
				widgetManager.updateAppWidget(provider, views);
			}
		}, 0, 5000);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer != null || timerTask != null) {
			timer.cancel();
			timerTask.cancel();
			timer = null;
			timerTask = null;
		}
	}
}
