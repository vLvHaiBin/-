package com.example.mobilesafe.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.Titlebar;

import android.app.Activity;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_Clean extends Activity {
	private PackageManager manager;
	private TextView tv_clean;
	private ListView lv_clean;
	ArrayList<AppInfo> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initview();
		initData();

	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Myadapter myadapter = new Myadapter();
			lv_clean.setAdapter(myadapter);
			fl.setVisibility(View.GONE);

			super.handleMessage(msg);
		}
	};
	private FrameLayout fl;
	private long totalSzie;

	private void initview() {
		list = new ArrayList<AppInfo>();
		setContentView(R.layout.activity_clean);
		tv_clean = (TextView) findViewById(R.id.tv_clean);
		lv_clean = (ListView) findViewById(R.id.lv_clean);
		fl = (FrameLayout) findViewById(R.id.fl_clean);
		
		Titlebar tl = (Titlebar) findViewById(R.id.title_clean);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
	}

	public void initData() {
		totalSzie = 0;
		manager = getPackageManager();

		getCache();

	}

	/**
	 * 
	 */
	private void getCache() {
		new Thread() {
			public void run() {
				List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
				for (PackageInfo info : installedPackages) {
					getCacheSize(info);
				}
				try {
					currentThread().sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				msg.obj = list;
				handler.sendMessage(msg);
			};
		}.start();
	}

	public void getCacheSize(PackageInfo packageInfo) {
		/* Class<?> loadClass = getClassLoader().loadClass("packageManager"); */
		try {
			Method declaredMethod = PackageManager.class.getDeclaredMethod("getPackageSizeInfo", String.class,
					IPackageStatsObserver.class);
			try {
				declaredMethod.invoke(manager, packageInfo.applicationInfo.packageName, new MyIPckage(packageInfo));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	class MyIPckage extends IPackageStatsObserver.Stub {

		private PackageInfo packageInfo;

		public MyIPckage(PackageInfo packageInfo) {
			this.packageInfo = packageInfo;

		}

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
			long cacheSize = pStats.cacheSize;
			AppInfo appInfo = new AppInfo();
			totalSzie += cacheSize;
			System.out.println(packageInfo.applicationInfo.packageName + "缓存" + cacheSize);
			Drawable icon = packageInfo.applicationInfo.loadIcon(manager);
			String name = (String) packageInfo.applicationInfo.loadLabel(manager).toString();
			appInfo.iv_image = icon;
			appInfo.tv_name = name;
			appInfo.tv_cache = Formatter.formatFileSize(Activity_Clean.this, cacheSize);
			list.add(appInfo);
			tv_clean.setText("垃圾：" + Formatter.formatFileSize(Activity_Clean.this, totalSzie));
		}

	}

	static class AppInfo {
		String tv_name;
		String tv_cache;
		Drawable iv_image;
	}

	static class ViewHolder {
		ImageView iv;
		TextView tv_name;
		TextView tv_cache;
	}

	class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(Activity_Clean.this, R.layout.adapter_clean, null);
				viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_clean_apk_icon);
				viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_clean_apk_name);
				viewHolder.tv_cache = (TextView) convertView.findViewById(R.id.tv_clean_apk_size);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.iv.setImageDrawable(list.get(position).iv_image);
			viewHolder.tv_cache.setText(list.get(position).tv_cache);
			viewHolder.tv_name.setText(list.get(position).tv_name);

			return convertView;
		}
	}

	public void click(View v) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method[] methods = PackageManager.class.getMethods();
		for (Method method : methods) {
			if (method.getName().equals("freeStorageAndNotify")) {
				method.invoke(manager, Integer.MAX_VALUE, new My());
			}

			Utils.toast(Activity_Clean.this, "全部清除成功");
			finish();
		}
	}

	public class My extends IPackageDataObserver.Stub {

		@Override
		public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {

		}

	}
}
