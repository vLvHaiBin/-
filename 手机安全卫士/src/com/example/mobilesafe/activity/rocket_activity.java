package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class rocket_activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Handler handler = new Handler();
		
		setContentView(R.layout.activity_rocket_bg);
		AlphaAnimation animation = new AlphaAnimation(0, 1);
		animation.setFillAfter(true);
		animation.setDuration(1000);
		ImageView iv_top = (ImageView) findViewById(R.id.iv_rocket_top);
		ImageView iv_bottom = (ImageView) findViewById(R.id.iv_rocket_bottom);
		iv_bottom.setAnimation(animation);
		iv_top.setAnimation(animation);
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
			}
		}, 1000);
	}
}
