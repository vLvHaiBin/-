package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.SmsUtils;
import com.example.mobilesafe.Utils.SmsUtils.BackUpCallBackSms;
import com.example.mobilesafe.ViewGroup.Titlebar;
import com.example.mobilesafe.ViewGroup.tiaomuRelativeLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author like 短信备份
 *
 */
public class Activity_Message_backups extends Activity {

	private RelativeLayout rl_message_backups;
	private TextView tv_message_backups_time;
	private tiaomuRelativeLayout findViewById;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		initView();
		initData();
	}

	private void initView() {
		setContentView(R.layout.activity_message_backups);
		Titlebar tl = (Titlebar) findViewById(R.id.title_message_backups);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		rl_message_backups = (RelativeLayout) findViewById(R.id.rl_message_backups);
		rl_message_backups.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showdialog();
			}

		});
		tv_message_backups_time = (TextView) findViewById(R.id.tv_message_backups_time);
		findViewById = (tiaomuRelativeLayout) findViewById(R.id.tiaomu_message_backups);

	}

	private void showdialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("短信备份");
		ab.setMessage("将进行短信备份，是否继续？");
		ab.setNegativeButton("取消", null);
		ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				showProgressdialog();

			}
		});
		ab.show();
	}

	protected void showProgressdialog() {
		final ProgressDialog bDialog = new ProgressDialog(this);
		bDialog.setTitle("提示");
		bDialog.setMessage("短信备份正在进行中");
		bDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	
		bDialog.show();
	
		new Thread(new Runnable() {

			@Override
			public void run() {
				
			try {
				SmsUtils.backUp(Activity_Message_backups.this, new BackUpCallBackSms() {
					
					@Override
					public void onBackUpSms(int process) {
						bDialog.setProgress(process);
					}
					
					@Override
					public void befor(int count) {
						bDialog.setMax(count);
					}
				});
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				bDialog.setMax(3471);
				
				bDialog.dismiss();
				}

				

				
			
		}).start();

	}

	private void initData() {

	}
}
