package com.example.mobilesafe.activity;

import java.util.ArrayList;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Dao.Dao_lockApp;
import com.example.mobilesafe.Utils.AppInfoUtils;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.ViewGroup.Titlebar;
import com.example.mobilesafe.bean.Bean_apk_info;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_app_lock extends Activity {
	private ListView lv_app_lock;

	private ArrayList<Bean_apk_info> apk_infos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
		initData();

	}

	private void initView() {
		Titlebar tl = (Titlebar) findViewById(R.id.title_app_lock);
		tl.iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.enter_alpha_activity, R.anim.quiet_alpha_activity);
			}
		});

		setContentView(R.layout.activity_app_lock);
		lv_app_lock = (ListView) findViewById(R.id.lv_app_lock);
		String status = Utils.sharprefrence_get(this, "applock", "-1");
		Utils.toast(this, status);
		if (status.equals("0")) {
			lv_app_lock.setFocusable(false);
			lv_app_lock.setEnabled(false);
			lv_app_lock.setClickable(false);
		}

	}

	private void initData() {
		lv_app_lock.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Dao_lockApp app = new Dao_lockApp(Activity_app_lock.this);
				String doqueryislock = Dao_lockApp.doqueryislock(apk_infos.get(position).packageName);
				ViewHolder tag = (ViewHolder) view.getTag();
				if (doqueryislock == null) {

					tag.iv_app_is_lock.setEnabled(true);
					Dao_lockApp.doInsert(apk_infos.get(position).packageName);
				} else {
					Dao_lockApp.doDelete(apk_infos.get(position).packageName);
					tag.iv_app_is_lock.setEnabled(false);
				}
			}
		});
		new Thread() {

			public void run() {
				final ArrayList<Bean_apk_info> apk_infos = AppInfoUtils.getAppInfo(Activity_app_lock.this);

				Message msg = handler.obtainMessage();
				msg.obj = apk_infos;
				handler.sendMessage(msg);
			};
		}.start();

	}

	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			apk_infos = (ArrayList<Bean_apk_info>) msg.obj;

			MyAdapter adapter = new MyAdapter();
			lv_app_lock.setAdapter(adapter);

		}
	};

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return apk_infos.size();
		}

		@Override
		public Object getItem(int position) {
			return apk_infos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(Activity_app_lock.this, R.layout.item_app_lock, null);
				viewHolder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_lock_image);
				viewHolder.iv_app_is_lock = (ImageView) convertView.findViewById(R.id.iv_app_lock_is_check);
				viewHolder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_lock_name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.iv_app_icon.setImageDrawable(apk_infos.get(position).apkIcon);
			viewHolder.iv_app_is_lock.setEnabled(false);
			viewHolder.tv_app_name.setText(apk_infos.get(position).apkname);

			return convertView;
		}

	}

	static class ViewHolder {
		ImageView iv_app_icon;
		TextView tv_app_name;
		ImageView iv_app_is_lock;
	}
}
