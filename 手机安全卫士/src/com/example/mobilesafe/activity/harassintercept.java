package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.ViewGroup.Titlebar;
import com.example.mobilesafe.ViewGroup.tiaomuRelativeLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * @author like
 * …ß»≈¿πΩÿ
 *
 */
public class harassintercept extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_harassintercept);
		initView();
		
	}

	private void initView() {
		Titlebar tl = (Titlebar) findViewById(R.id.title_harass_intercept);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		tiaomuRelativeLayout add_black = (tiaomuRelativeLayout) findViewById(R.id.tiaomu_add_black);
		add_black.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(harassintercept.this,Activity_add_black.class));
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		tiaomuRelativeLayout layout = (tiaomuRelativeLayout) findViewById(R.id.tiaomu_blacklist);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(harassintercept.this,Activity_Blacklist.class));
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
	}
}
