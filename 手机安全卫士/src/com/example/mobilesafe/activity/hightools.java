package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.Titlebar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * @author like 高级工具界面
 */
public class hightools extends Activity implements OnClickListener {

	private Titlebar title_high_tool;
	private RelativeLayout rl_place_query;
	private RelativeLayout rl_phone_query;
	private RelativeLayout rl_message_backups;
	private RelativeLayout rl_program_lock;
	private RelativeLayout rl_toast_place;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hightools);

		initView();
		initData();
	}

	private void initView() {
		title_high_tool = (Titlebar) findViewById(R.id.title_high_tools);

		rl_place_query = (RelativeLayout) findViewById(R.id.rl_place_query);
		rl_place_query.setOnClickListener(this);

		rl_phone_query = (RelativeLayout) findViewById(R.id.rl_phone_query);
		rl_phone_query.setOnClickListener(this);

		rl_message_backups = (RelativeLayout) findViewById(R.id.rl_message_backups);
		rl_message_backups.setOnClickListener(this);

		rl_program_lock = (RelativeLayout) findViewById(R.id.rl_program_lock);
		rl_program_lock.setOnClickListener(this);

		rl_toast_place = (RelativeLayout) findViewById(R.id.rl_toast_place);
		rl_toast_place.setOnClickListener(this);
		Titlebar tl = (Titlebar) findViewById(R.id.title_high_tools);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
	}

	private void initData() {
		titlebarBackevent();// titlebar

	}

	/**
	 * titlebar的点击返回事件
	 */
	private void titlebarBackevent() {

		title_high_tool.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.rl_place_query:// 归属地查询
			startActivity(new Intent(this, place_query.class));
			overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

			break;

		case R.id.rl_phone_query:// 常用电话号码查询

			break;

		case R.id.rl_message_backups:// 短信备份
			startActivity(new Intent(this, Activity_Message_backups.class));
			overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			break;

		case R.id.rl_program_lock:// 程序锁
			startActivity(new Intent(this, Activity_app_lock.class));
			overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);

			break;

		case R.id.rl_toast_place:// 归属地显示位置
			startActivity(new Intent(this, placetoastActivity.class));
			overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			break;
		}
	}

}
