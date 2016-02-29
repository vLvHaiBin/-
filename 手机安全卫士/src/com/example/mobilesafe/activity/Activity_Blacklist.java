package com.example.mobilesafe.activity;

import java.util.ArrayList;
import java.util.Random;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Dao.Dao_blacklist;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.Titlebar;
import com.example.mobilesafe.adapter.adapter_listview_blacklist;
import com.example.mobilesafe.bean.Bean_blacklist;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author like 黑名单设置页面
 */
public class Activity_Blacklist extends Activity {

	private Titlebar title_blacklist;
	private ListView lv_blacklist;
	private Dao_blacklist blacklist;
	public int shumu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blacklist);
		initView();
		initData();
	}

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				adapter_listview_blacklist adapter = new adapter_listview_blacklist(Activity_Blacklist.this,
						(ArrayList<Bean_blacklist>) msg.obj);
				lv_blacklist.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;
			case 2:
				fl_black.setVisibility(View.GONE);
				String.valueOf(msg.obj);
				shumu = (Integer) msg.obj;
				if (shumu % 20 != 0) {
					shumu = shumu / 20 + 1;
				} else {
					shumu = shumu / 20;
				}
				Utils.toast(Activity_Blacklist.this, "" + shumu);
				tv_pager_number.setText("1" + "/" + String.valueOf(shumu));
				break;
			}

		}
	};

	private FrameLayout fl_black;
	private TextView tv_pager_number;
	private EditText et_pager;
	private int currentpage = 0;
	private Thread thread;
	private ArrayList<Bean_blacklist> doqueryAll;

	private void initView() {
		et_pager = (EditText) findViewById(R.id.et_yehsu);
		tv_pager_number = (TextView) findViewById(R.id.tv_black_yeshu);
		fl_black = (FrameLayout) findViewById(R.id.fl_black);
		title_blacklist = (Titlebar) findViewById(R.id.title_blacklist);

		title_blacklist.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});
		lv_blacklist = (ListView) findViewById(R.id.lv_blacklist);
	}

	private void initData() {
		blacklist = new Dao_blacklist(this);
		getblacklist(0, "20");
		getblacklistnumber();

	}

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void getblacklistnumber() {

		try {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					fl_black.setVisibility(View.VISIBLE);
					int pagenumber = Dao_blacklist.getcallNumber();
					Dao_blacklist.getcallNumber();
					Message msg = handler.obtainMessage();
					msg.obj = pagenumber;
					msg.what = 2;
					handler.sendMessage(msg);
				}
			});
			thread.start();
		} finally {

		}

	}

	/**
	 * 
	 */
	private void getblacklist(final int page, final String number) {

		try {
			thread = new Thread(new Runnable() {
				@Override
				public void run() {
					final String pagenumber = String.valueOf(page * 20);
					Random status = new Random();
					doqueryAll = Dao_blacklist.doqueryAll(Activity_Blacklist.this, pagenumber, number);

					Message msg = handler.obtainMessage();
					msg.obj = doqueryAll;
					msg.what = 1;
					handler.sendMessage(msg);
				}
			});
			thread.start();
		} finally {

		}

	}

	public void upPage(View v) {
		currentpage--;
		if (currentpage < 0) {
			currentpage = 0;
		} else {
			getblacklist(currentpage, "20");
		}
		int current = currentpage + 1;
		tv_pager_number.setText("" + current + "/" + String.valueOf(shumu));
	}

	public void nextPage(View v) {
		currentpage++;
		if (currentpage < 1) {
			currentpage = 0;
		} else if (currentpage == shumu) {
			return;
		} else if (currentpage > shumu) {
			currentpage = shumu - 1;
		}
		getblacklist(currentpage, "20");
		int current = currentpage + 1;
		tv_pager_number.setText("" + current + "/" + String.valueOf(shumu));

	}
}
