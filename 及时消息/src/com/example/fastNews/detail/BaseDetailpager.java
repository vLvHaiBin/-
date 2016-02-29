package com.example.fastNews.detail;

import android.app.Activity;
import android.view.View;

public class BaseDetailpager {
	
	public Activity activity;
	public View v;

	public BaseDetailpager(Activity activity) {
		this.activity = activity;
		initView();
	}

	protected void initView() {
		
	}
	
	public void initdata(){
		
	};
}
