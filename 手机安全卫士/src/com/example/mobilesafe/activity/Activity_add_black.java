package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Dao.Dao_blacklist;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.Titlebar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class Activity_add_black extends Activity {
	private EditText et_add_name;
	private EditText et_add_number;
	private String name;
	private String phone;
	private Titlebar tl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_black);
		initView();
		initData();
	}

	private void initView() {
		tl = (Titlebar) findViewById(R.id.title_add_black);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		et_add_name = (EditText) findViewById(R.id.et_black_name);
		et_add_number = (EditText) findViewById(R.id.et_black_number);
	}

	private void initData() {
		
	}
	
	public void contactChioce(View v){
		Intent intent = new Intent(Activity_add_black.this, Contact.class);
		startActivityForResult(intent, 2);
		overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 2){
			name = data.getStringExtra("name");
			phone = data.getStringExtra("phone");
			et_add_name.setText(name);
			et_add_number.setText(phone);
			
		}
	}
	
	public void affirm(View v){
		String name = et_add_name.getText().toString().trim();
		String number = et_add_number.getText().toString().trim();
		if(TextUtils.isEmpty(name)){
			name = "未知";
		}
		
		if(TextUtils.isEmpty(number)){
			Utils.toast(Activity_add_black.this, "请输入号码");
		}else{
			Dao_blacklist blacklist = new Dao_blacklist(Activity_add_black.this);
			blacklist.doInsert(Activity_add_black.this, name, number, "2");
			Utils.toast(this, "添加成功");
		}
		finish();
	}
}
