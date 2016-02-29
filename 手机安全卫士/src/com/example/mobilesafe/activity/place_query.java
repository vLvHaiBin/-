package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Dao.AddressDAO;
import com.example.mobilesafe.ViewGroup.Titlebar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class place_query extends Activity {
	private EditText et_input;
	private TextView tv_query_result;
	private Titlebar title_query_number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_query);
		
		initView();
		initData();
	}

	private void initView() {
		title_query_number = (Titlebar) findViewById(R.id.title_query_number);
		et_input = (EditText) findViewById(R.id.et_input_number);
		editcontentchangelistener();
		tv_query_result = (TextView) findViewById(R.id.tv_query_result);
	}

	/**
	 * edittext内容变化监听器
	 */
	private void editcontentchangelistener() {
		et_input.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String address = AddressDAO.getAddress(s.toString());
				tv_query_result.setText(address);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initData() {
		titlebarBackevent();
	}
	
	/**
	 * @param v
	 * 点击按钮，执行查询事件
	 */
	public void click(View v){
		
		String number = et_input.getText().toString();
		String address = AddressDAO.getAddress(number);
		AddressDAO.animation(et_input, number, this);
		vibrator();
		tv_query_result.setText(address);
	}

	/**
	 * 手机震动
	 */
	private void vibrator() {
		
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(new long[]{1000,1000,2000},-1);//-1表示只执行一次。0~。。。表示从数组的第几个开始循环
//		vibrator.cancel();取消震动
	}
	
	private void titlebarBackevent() {

		title_query_number.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
	}
}
