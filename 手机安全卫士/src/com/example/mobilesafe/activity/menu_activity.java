package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;

import com.example.mobilesafe.Utils.ModifyOffset;

import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.adapter.MyMenuGridViewAdapter;
import com.example.mobilesafe.server.AddressServer;
import com.example.mobilesafe.server.WatchDogService;
import com.example.mobilesafe.server.rocketserver;
import com.lidroid.xutils.HttpUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class menu_activity extends Activity {

	private TextView tv_score;
	private int width;
	private int height;

	public int[] image = new int[] { R.drawable.rubbish_clean, R.drawable.phone_accelarate, R.drawable.flow_management,
			R.drawable.harass_intercept, R.drawable.permision_management,
			R.drawable.rightsmanagement_security_permission, R.drawable.phone_clean, R.drawable.virus_cleaning };

	public String[] text = new String[] { "垃圾清理", "进程管理", "流量管理", "骚扰拦截", "软件管理", "手机防盗", "高级工具", "病毒查杀" };

	private GridView gv_menu_content;

	private ImageView iv_menu;

	private PopupWindow mPopupWindow;

	private View v;
	private EditText et_password;
	private EditText et_password_confirm;
	private Button btn_confirm;
	private Button btn_cancle;
	private AlertDialog create;
	private String password;
	private String confirm_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu_activity);
		initView();
		initData();
		// startService(new Intent(this, rocketserver.class));
		startService(new Intent(this, WatchDogService.class));
	}

	public void initView() {
		String isUpdate = Utils.sharprefrence_get(this, "auto_update", "-1");
		if (isUpdate.equals("1")) {
			Utils.getVersionCode(this);// 如果获取的版本号和服务器获取的小，就更新；
			HttpUtils httpUtils = new HttpUtils();
			Utils.toast(this, "连接服务器失败，更新失败");
			/* httpUtils.download("下载的url", "下载文件的位置", callback); */
		}
		iv_menu = (ImageView) findViewById(R.id.iv_menu);
		v = View.inflate(menu_activity.this, R.layout.popwindows, null);
		TextView tv_setting = (TextView) v.findViewById(R.id.tv_1);

		onclickevent(tv_setting);// 点击事件
		tv_score = (TextView) findViewById(R.id.tv_score);
		gv_menu_content = (GridView) findViewById(R.id.gv_content_gridview);
		gv_menu_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:// 垃圾清理
					startActivity(new Intent(menu_activity.this, Activity_Clean.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
					break;
				case 1:// 进程管理
					startActivity(new Intent(menu_activity.this, Activity_app_process.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
					break;
				case 2:// 流量管理

					break;
				case 3:// 骚扰拦截
					startActivity(new Intent(menu_activity.this, harassintercept.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

					break;
				case 4:// 软件管理
					startActivity(new Intent(menu_activity.this, Activity_sw_manage.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
					break;
				case 5:// 手机防盗
					disposepassword();

					break;
				case 6:// 高级工具
					startActivity(new Intent(menu_activity.this, hightools.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

					break;
				case 7:// 病毒查杀
					startActivity(new Intent(menu_activity.this, Activity_virus_find.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

					break;
				}
			}

			/**
			 * 对防盗页面密码的所有事件处理
			 */
			private void disposepassword() {

				String getpassword = Utils.sharprefrence_get(menu_activity.this, "password", "-1");
				if (TextUtils.equals(getpassword, "-1")) {
					showsetpassworddialog();
					passwordsetting();
				} else {
					showentersettingdialog();
				}
			}
		});

	}

	/**
	 * 输入进入密码
	 * 
	 */
	protected void showentersettingdialog() {

		AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
		AlertDialog mcreate = mbuilder.create();
		View mv = View.inflate(menu_activity.this, R.layout.dialog_guard_enter_password, null);
		EditText et_mpassword = (EditText) mv.findViewById(R.id.et_mpassword);
		Button btn_mconfirm = (Button) mv.findViewById(R.id.btn_maffirm);
		Button btn_mcancle = (Button) mv.findViewById(R.id.btn_mcancle);
		mcreate.setView(mv);
		mcreate.show();

		onclick(et_mpassword, btn_mconfirm, btn_mcancle, mcreate);
	}

	/**
	 * 对进入密码界面的点击事件处理
	 * 
	 * @param et_mpassword
	 * @param btn_mconfirm
	 * @param btn_mcancle
	 * @param mcreate
	 */
	private void onclick(final EditText et_mpassword, Button btn_mconfirm, Button btn_mcancle,
			final AlertDialog mcreate) {
		btn_mcancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mcreate.dismiss();
			}
		});

		btn_mconfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String getpassword = Utils.MD5_encode(et_mpassword.getText().toString());
				String savepassword = Utils.sharprefrence_get(menu_activity.this, "password", "-1");
				if (TextUtils.equals(getpassword, savepassword)) {
					mcreate.dismiss();
					startActivity(new Intent(menu_activity.this, guard_activity.class));
					overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

				} else {
					Utils.toast(menu_activity.this, "密码不正确，请重新输入");
				}
			}
		});
	}

	/**
	 * 密码的输入确认事件
	 */
	protected void passwordsetting() {

		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				create.dismiss();
			}
		});

		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				password = et_password.getText().toString();
				confirm_password = et_password_confirm.getText().toString();
				if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm_password)) {
					if (TextUtils.equals(password, confirm_password)) {
						Utils.sharprefrence_save(menu_activity.this, "password", Utils.MD5_encode(confirm_password));
						startActivity(new Intent(menu_activity.this, guard_activity.class));

						Utils.toast(menu_activity.this, "正确");
						create.dismiss();
					} else {
						Utils.toast(menu_activity.this, "两次密码不一致，请重新输入");
					}
				} else {
					Utils.toast(menu_activity.this, "密码不能为空");
				}
			}
		});
	}

	/**
	 * 手机防盗首次进入页面设置密码
	 */
	protected void showsetpassworddialog() {// 主题会影响显示
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		create = builder.create();
		View v = View.inflate(menu_activity.this, R.layout.dialog_guard_password, null);
		et_password = (EditText) v.findViewById(R.id.et_password);
		et_password_confirm = (EditText) v.findViewById(R.id.et_password_affirm);
		btn_confirm = (Button) v.findViewById(R.id.btn_affirm);
		btn_cancle = (Button) v.findViewById(R.id.btn_cancle);
		create.setView(v);
		create.show();
	}

	/**
	 * @param tv_setting
	 *            pop点击事件
	 */
	private void onclickevent(TextView tv_setting) {
		tv_setting.setOnClickListener(new OnClickListener() {// popwindows的点击事件

			@Override
			public void onClick(View v) {
				startActivity(new Intent(menu_activity.this, settingActivity.class));
				mPopupWindow.dismiss();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		// 图标的点击事件
		iv_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopWindows();

			}
		});
	}

	/**
	 * popwindows
	 */
	@SuppressWarnings("deprecation")
	protected void showPopWindows() {

		if (mPopupWindow == null) {
			mPopupWindow = new PopupWindow(v, v.getLayoutParams().WRAP_CONTENT, v.getLayoutParams().WRAP_CONTENT);
		}
		mPopupWindow.setFocusable(true);// pop里面的控件是没有焦点的，需要设置；
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.mz_card_bg_light_normal));
		mPopupWindow.showAsDropDown(iv_menu, -200, 0);

	}

	public void initData() {

		MyMenuGridViewAdapter adapter = new MyMenuGridViewAdapter(image, text, this);
		gv_menu_content.setAdapter(adapter);
		// 判断自动更新是否开启
		String state = Utils.sharprefrence_get(this, "自动更新设置", "-1");
		if (TextUtils.equals(state, "1")) {

		}
		guard();// 防盗页面处理
		place_show();// 归属地设置
	}

	/**
	 * 防盗页面处理
	 * 
	 */
	private void guard() {

		String sim = Utils.sharprefrence_get(menu_activity.this, "sim", null);
		if (TextUtils.isEmpty(sim)) {

			TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			String simSerialNumber = manager.getSimSerialNumber();
			Utils.sharprefrence_save(menu_activity.this, "sim", simSerialNumber);
		} else {
			Utils.toast(menu_activity.this, "sim卡已绑定");
		}

		/*
		 * String guardstate = Utils.sharprefrence_get(this, "guard", "-1");
		 * if(guardstate.equals("1")){
		 * 
		 * 
		 * }
		 */
	}

	/**
	 * 归属地页面处理
	 * 
	 */
	private void place_show() {

		String place_setting_state = Utils.sharprefrence_get(menu_activity.this, "place_show", "-1");
		if (place_setting_state.equals("1")) {
			startService(new Intent(this, AddressServer.class));
			Utils.toast(menu_activity.this, "归属地服务开启");
		} else {

		}

	}

}
