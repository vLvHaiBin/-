package com.example.fastNews.base;




import com.example.fastNews.R;

import android.app.Activity;
import android.view.View;

public class basePager {

	public Activity activity;
	public  View v;

	public basePager(Activity activity) {
		this.activity = activity;
		initView();
		
	}

	public void initView() {
		v = View.inflate(activity, R.layout.newspager, null);
	};

	public void initData() {

	}

}
