package com.example.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Activity_lock_app_enter extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
		initdata();
	}

	private void initView() {

	}

	private void initdata() {

	}
}
