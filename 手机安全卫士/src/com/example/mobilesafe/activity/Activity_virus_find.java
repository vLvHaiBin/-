package com.example.mobilesafe.activity;

import java.util.List;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Dao.virusDAO;
import com.example.mobilesafe.Utils.Md5Utils;
import com.example.mobilesafe.ViewGroup.Titlebar;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Activity_virus_find extends Activity {
	public ImageView iv_virus_find;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initView();
		initdata();
	}

	private void initView() {
		setContentView(R.layout.activity_virus_find);
		iv_virus_find = (ImageView) findViewById(R.id.iv_virus_find);
		sv_show = (ScrollView) findViewById(R.id.sv_virus_show);
		Titlebar tl = (Titlebar) findViewById(R.id.title_virus_find);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		ll_show = (LinearLayout) findViewById(R.id.ll_show_virus);
	}

	private void initdata() {
		RotateAnimation animation = new RotateAnimation(0, 360000, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(600000);
		animation.setRepeatCount(2);
		iv_virus_find.startAnimation(animation);

		new Thread() {
			@Override
			public void run() {

				PackageManager packageManager = getPackageManager();
				List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
				for (PackageInfo packageInfo : installedPackages) {
					saomiaoInfo info = new saomiaoInfo();
					// 获取到当前手机上面的app的名字
					String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
					info.appName = appName;
					String packageName = packageInfo.applicationInfo.packageName;
					info.packageName = packageName;
					// 首先需要获取到每个应用程序的目录

					String sourceDir = packageInfo.applicationInfo.sourceDir;
					// 获取到文件的md5
					String md5 = Md5Utils.getFileMd5(sourceDir);
					String virus = virusDAO.getVirus(md5);
					if (virus == null) {
						info.isVirus = false;
					} else {
						info.isVirus = true;
					}

					Message msg = handler.obtainMessage();
					msg.obj = info;
					msg.what = 1;
					handler.sendMessage(msg);
				}
				Message message = handler.obtainMessage();
				message.what = 2;
				handler.sendEmptyMessage(2);

			}

		}.start();

	}

	static class saomiaoInfo {
		boolean isVirus;
		String appName;
		String packageName;
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				saomiaoInfo sInfo = (saomiaoInfo) msg.obj;
				TextView tv = new TextView(Activity_virus_find.this);
				if (sInfo.isVirus) {
					tv.setText(sInfo.appName + "有病毒");
					tv.setTextColor(Color.RED);
				} else {
					tv.setText(sInfo.appName + ":" + sInfo.packageName);
				}
				sv_show.post(new Runnable() {

					@Override
					public void run() {
						sv_show.fullScroll(sv_show.FOCUS_DOWN);// scroll自动滚动
					}
				});
				ll_show.addView(tv);
				break;

			case 2:
				iv_virus_find.clearAnimation();

				break;
			}

		}
	};
	private ScrollView sv_show;
	private LinearLayout ll_show;
}
