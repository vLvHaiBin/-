package com.example.mobilesafe.server;

import java.util.List;

import com.example.mobilesafe.Dao.Dao_lockApp;
import com.example.mobilesafe.activity.Activity_lock_app_enter;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.ViewDebug.FlagToString;

public class WatchDogService extends Service {

	private ActivityManager activityManager;

	@Override
	public IBinder onBind(Intent intent) {
		return new mbind();
	}

	class mbind extends Binder {
		public WatchDogService getWatchDogServers() {
			return WatchDogService.this;
		}
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	// ��ʱֹͣ�����İ���
	private String tempStopProtectPackageName;

	private class WatchDogReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals("com.itheima.mobileguard.stopprotect")) {
				// ��ȡ��ֹͣ�����Ķ���

				tempStopProtectPackageName = intent.getStringExtra("packageName");
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				tempStopProtectPackageName = null;
				// �ù���Ϣ
				falg = false;
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				// �ù������ɻ�
				if (falg == false) {
					startWatDog();
				}
			}

		}

	}

	@Override
	public void onCreate() {
		super.onCreate();

		app = new Dao_lockApp(getApplication());

		// ע��㲥������

		receiver = new WatchDogReceiver();

		IntentFilter filter = new IntentFilter();
		// ֹͣ����
		filter.addAction("com.itheima.mobileguard.stopprotect");

		// ע��һ�������Ĺ㲥
		/**
		 * ����Ļ��ס��ʱ�򡣹�����Ϣ ��Ļ������ʱ���ù������
		 */
		filter.addAction(Intent.ACTION_SCREEN_OFF);

		filter.addAction(Intent.ACTION_SCREEN_ON);

		registerReceiver(receiver, filter);

		// ��ȡ�����̹�����

		activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		// 1 ������Ҫ��ȡ����ǰ������ջ

		// 2ȡ����ջ�����������

		startWatDog();
	}

	// ��ǵ�ǰ�Ŀ��ȹ��Ƿ�ͣ����
	private boolean falg = false;
	private List<String> appLockInfos;
	private WatchDogReceiver receiver;
	private Dao_lockApp app;

	private void startWatDog() {

		new Thread() {
			public void run() {
				falg = true;
				while (falg) {
					// ���������һֱ�ں�̨���С�Ϊ�˱������������
					// ��ȡ����ǰ�������е�����ջ
					@SuppressWarnings("deprecation")
					List<RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
					// ��ȡ��������Ľ���
					RunningTaskInfo taskInfo = tasks.get(0);
					// ��ȡ�����Ӧ�ó���İ���
					String packageName = taskInfo.topActivity.getPackageName();

					System.out.println(packageName);
					// �ù���Ϣһ��
					SystemClock.sleep(30);
					// ֱ�Ӵ����ݿ�������ҵ�ǰ������
					// ��������Ż����ĳɴ��ڴ浱��Ѱ��
					String doqueryislock = app.doqueryislock(packageName);
					if (doqueryislock != null) {
						// if(dao.find(packageName)){
						// System.out.println("�ڳ��������ݿ�����");

						// ˵����Ҫ��ʱȡ������
						// ����Ϊ�û���������ȷ������

						Intent intent = new Intent(WatchDogService.this, Activity_lock_app_enter.class);
						/**
						 * ��Ҫע�⣺������ڷ���������activity�������Ļ�����Ҫ����flag
						 */
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						// ֹͣ�����Ķ���
						intent.putExtra("packageName", packageName);

						startActivity(intent);

					}

				}

			};
		}.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		falg = false;
		unregisterReceiver(receiver);
		receiver = null;
	}

}
