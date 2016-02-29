package com.example.mobilesafe.activity;

import java.util.ArrayList;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.AppInfoUtils;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.Titlebar;
import com.example.mobilesafe.adapter.adapter_listview_sw_manager;
import com.example.mobilesafe.bean.Bean_apk_info;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @author like 软件管理
 */
public class Activity_sw_manage extends Activity implements OnClickListener {

	private ListView lv_sw_manage;
	private TextView tv_sw_manage_rom;
	private TextView tv_sw_manage_ram;
	private long rom_free;
	private long ram_free;
	private adapter_listview_sw_manager adapter_listview_sw_manager;
	private ArrayList<Bean_apk_info> userApp;
	private ArrayList<Bean_apk_info> systemApp;
	private PopupWindow popupWindow = null;
	private Bean_apk_info apk_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		initVeiw();
		initData();
	}

	private Handler handler = new Handler(/**
											 * @author like handler处理事件
											 */
			new Callback() {

				@Override
				public boolean handleMessage(Message msg) {
					adapter_listview_sw_manager = new adapter_listview_sw_manager(Activity_sw_manage.this,
							(ArrayList<Bean_apk_info>) msg.obj);
					lv_sw_manage.setAdapter(adapter_listview_sw_manager);
					userApp = adapter_listview_sw_manager.userApp;
					systemApp = adapter_listview_sw_manager.systemApp;
					listscrolllisetner();
					lv_sw_manage.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

							Object object = lv_sw_manage.getItemAtPosition(position);
							apk_info = (Bean_apk_info) object;
							if (object != null && object instanceof Bean_apk_info) {
								if (popupWindow != null && popupWindow.isShowing()) {
									popupWindow.dismiss();
									popupWindow = null;
								} else {
									showPopWindows(view);
								}
							}
						}

						/**
						 * @param view
						 * 
						 *            popwindows的显示
						 */
						private void showPopWindows(View view) {
							View v = View.inflate(Activity_sw_manage.this, R.layout.item_popup, null);
							LinearLayout ll_uninstall = (LinearLayout) v.findViewById(R.id.ll_uninstall);
							LinearLayout ll_start = (LinearLayout) v.findViewById(R.id.ll_start);
							LinearLayout ll_detail = (LinearLayout) v.findViewById(R.id.ll_detail);
							ll_uninstall.setOnClickListener(Activity_sw_manage.this);
							ll_start.setOnClickListener(Activity_sw_manage.this);
							ll_detail.setOnClickListener(Activity_sw_manage.this);

							popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
									true);
							int[] location = new int[2];
							view.getLocationInWindow(location);//获取listview条目的x,y
							popupWindow.setTouchable(true);// 都必须设置
							popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 必须设置
							/*
							 * popupWindow.setAnimationStyle(R.anim.
							 * translate_edit);
							 */
							popupWindow.showAtLocation(view, Gravity.LEFT + Gravity.TOP,
									Utils.getscreendensity(Activity_sw_manage.this, 45), location[1]);
							ScaleAnimation animation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f,
									Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);//给pop设置动画
							AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
							alphaAnimation.setDuration(300);
							animation.setDuration(500);
							v.startAnimation(animation);
							v.startAnimation(alphaAnimation);

						}

					});
					return true;
				}

				/**
				 * listview的滚动事件
				 */
				private void listscrolllisetner() {
					lv_sw_manage.setOnScrollListener(new OnScrollListener() {

						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {

						}

						@Override
						public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
								int totalItemCount) {
							isshowpopwindows();
							if (firstVisibleItem == 0) {
								tv_sw_manager_show.setVisibility(View.GONE);
							}

					else if (firstVisibleItem > userApp.size() + 1) {
								tv_sw_manager_show.setVisibility(View.VISIBLE);
								tv_sw_manager_show.setText("系统应用(" + systemApp.size() + ")");
							} else {
								tv_sw_manager_show.setVisibility(View.VISIBLE);
								tv_sw_manager_show.setText("用户应用(" + userApp.size() + ")");
							}

						}
					});
				}
			});
	private TextView tv_sw_manager_show;

	/**
	 * 是否消除pop
	 */
	private void isshowpopwindows() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		}
	}

	private void initVeiw() {
		setContentView(R.layout.activity_sw_manage);
		lv_sw_manage = (ListView) findViewById(R.id.lv_sw_manage);

		tv_sw_manage_rom = (TextView) findViewById(R.id.tv_sw_manage_rom);
		tv_sw_manage_ram = (TextView) findViewById(R.id.tv_sw_manage_ram);
		tv_sw_manager_show = (TextView) findViewById(R.id.tv_sw_manager_show);
		Titlebar tl = (Titlebar) findViewById(R.id.title_sw_manager);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
	}

	private void initData() {
		newThread();
		get_ramAndrom();
	}

	/**
	 * 获取剩余内存
	 */
	private void get_ramAndrom() {
		rom_free = Environment.getDataDirectory().getFreeSpace();// 获取内存可用空间
		ram_free = Environment.getExternalStorageDirectory().getFreeSpace();// 获取sd卡可用空间
		tv_sw_manage_rom.setText("内存可用:" + android.text.format.Formatter.formatFileSize(this, rom_free));// 格式化
		tv_sw_manage_ram.setText("sd卡可用:" + android.text.format.Formatter.formatFileSize(this, ram_free));
	}

	/**
	 * 
	 */
	private void newThread() {
		new Thread(new Runnable() {
			public void run() {
				ArrayList<Bean_apk_info> appInfo = AppInfoUtils.getAppInfo(Activity_sw_manage.this);
				Message message = handler.obtainMessage();
				message.obj = appInfo;
				handler.sendMessage(message);
			}
		}).start();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		isshowpopwindows();
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * pop的点击事件
	 */
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		/*
		 * //分享 case R.id.ll_share:
		 * 
		 * Intent share_localIntent = new Intent("android.intent.action.SEND");
		 * share_localIntent.setType("text/plain");
		 * share_localIntent.putExtra("android.intent.extra.SUBJECT", "f分享");
		 * share_localIntent.putExtra("android.intent.extra.TEXT", "Hi！推荐您使用软件："
		 * + clickAppInfo.getApkName()+"下载地址:"+
		 * "https://play.google.com/store/apps/details?id="+clickAppInfo.
		 * getApkPackageName());
		 * this.startActivity(Intent.createChooser(share_localIntent, "分享"));
		 * popupWindowDismiss();
		 * 
		 * break;
		 */

		// 运行
		case R.id.ll_start:

			Intent start_localIntent = this.getPackageManager().getLaunchIntentForPackage(apk_info.getPackageName());
			this.startActivity(start_localIntent);
			isshowpopwindows();
			break;
		// 卸载
		case R.id.ll_uninstall:

			Intent uninstall_localIntent = new Intent("android.intent.action.DELETE",
					Uri.parse("package:" + apk_info.getPackageName()));
			startActivity(uninstall_localIntent);
			isshowpopwindows();
			break;
			//详情页
		case R.id.ll_detail:
			Intent detail_intent = new Intent();
			detail_intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			detail_intent.addCategory(Intent.CATEGORY_DEFAULT);
			detail_intent.setData(Uri.parse("package:" + apk_info.getPackageName()));
			startActivity(detail_intent);
			isshowpopwindows();
			break;
		}
	}
}
