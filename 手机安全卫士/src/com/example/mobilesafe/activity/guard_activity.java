package com.example.mobilesafe.activity;

import java.security.Guard;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.SettingRelativeLayout;
import com.example.mobilesafe.ViewGroup.Titlebar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;

/**
 * @author like
 *��������ҳ��
 */
public class guard_activity extends Activity {

	private Titlebar titlebar;
	private SettingRelativeLayout sr_guard;
	private RelativeLayout rl_select_contact;
	private TextView tv_select_contact;
	private TextView tv_select_state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guard);
		initView();
		initData();
		String sim = Utils.sharprefrence_get(guard_activity.this, "sim", "-1");
		Utils.toast(this, "sss" + sim);
	}

	private void initView() {
		titlebar = (Titlebar) findViewById(R.id.guard_title);
		sr_guard = (SettingRelativeLayout) findViewById(R.id.sr_guard);
		rl_select_contact = (RelativeLayout) findViewById(R.id.rl_select_contact);
		rl_select_contact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(guard_activity.this, Contact.class);
				startActivityForResult(intent, 1);
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		tv_select_contact = (TextView) findViewById(R.id.tv_contact_number);
		tv_select_state = (TextView) findViewById(R.id.tv_select_state);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			String phone = data.getStringExtra("phone");
			Utils.sharprefrence_save(guard_activity.this, "phone", phone);
			tv_select_state.setText("��ѡ��");
			tv_select_contact.setText(phone);
		}

	}

	private void initData() {
		selectcontact();//ѡ����ϵ��

		titlebarBackevent();// title���õ�������¼�

	}

	/**
	 * ѡ����ϵ��
	 */
	private void selectcontact() {
		
		String phone = Utils.sharprefrence_get(guard_activity.this, "phone", null);
		if (!TextUtils.isEmpty(phone)) {
			tv_select_contact.setText(phone);
			tv_select_state.setText("��ѡ��");
		}
	}

	/**
	 * titlebar�ĵ�������¼�
	 */
	private void titlebarBackevent() {

		titlebar.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
	}

}
