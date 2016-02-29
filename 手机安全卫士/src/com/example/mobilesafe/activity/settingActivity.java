package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.SettingRelativeLayout;
import com.example.mobilesafe.ViewGroup.Titlebar;
import com.example.mobilesafe.ViewGroup.ToggleButton;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class settingActivity extends Activity {

	public SettingRelativeLayout sr_update_setting;
	private Titlebar titlebar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_activity);
		initView();
	}

	private void initView() {
		titlebar = (Titlebar) findViewById(R.id.title);
		titlebar.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		updateSetting();// 更新选项
	}

	private void updateSetting() {
		sr_update_setting = (SettingRelativeLayout) findViewById(R.id.sr_update_setting);

	}
}
