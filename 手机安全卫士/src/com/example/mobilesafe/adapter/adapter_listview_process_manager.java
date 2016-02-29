/*package com.example.mobilesafe.adapter;

import java.util.ArrayList;

import com.example.mobilesafe.R;
import com.example.mobilesafe.bean.Bean_app_process;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

*//**
 * @author like 进程管理适配器
 *//*
public class adapter_listview_process_manager extends BaseAdapter {

	private ArrayList<Bean_app_process> apk_infos;
	private Context context;
	public ArrayList<Bean_app_process> userApp;
	public ArrayList<Bean_app_process> systemApp;
	private Bean_app_process bean_apk_info;
	public static boolean ischeck = false;
	public  ViewHolder viewholder;

	public adapter_listview_process_manager(Context context, final ArrayList<Bean_app_process> apk_infos) {
	
		this.context = context;
		this.apk_infos = apk_infos;
		if(userApp != null){
			userApp.clear();
		}
		if(systemApp != null){
			systemApp.clear();
		}
		
		userApp = new ArrayList<Bean_app_process>();
		systemApp = new ArrayList<Bean_app_process>();

		for (Bean_app_process bean_apk_info : apk_infos) {
			if (bean_apk_info.isUser) {// 将用户和系统应用装入不同的集合
				userApp.add(bean_apk_info);
			} else {
				systemApp.add(bean_apk_info);
			}
		}
	}

	@Override
	public int getCount() {
		return userApp.size() + systemApp.size() + 1;
	}

	@Override
	public Bean_app_process getItem(int position) {
		Bean_app_process mbean_apk_info = null;
		if (position == userApp.size()) {
			return null;
		} else if (position < userApp.size()) {
			mbean_apk_info = userApp.get(position);
		} else if (position > userApp.size()) {
			mbean_apk_info = systemApp.get(position - (userApp.size() + 1));
		}
		return mbean_apk_info;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (position == (userApp.size())) {
			Toast.makeText(context, "sss"+userApp.size(), 0).show();
			TextView tv_system = new TextView(context);
			tv_system.setText("系统应用(" + systemApp.size() + ")");
			tv_system.setBackgroundColor(Color.GRAY);
			return tv_system;

		} else if (position < userApp.size()) {
			bean_apk_info = userApp.get(position);
		} else if (position > userApp.size()) {
			bean_apk_info = systemApp.get(position - (userApp.size() + 1));
		}

		if (convertView != null && convertView instanceof RelativeLayout) {
			viewholder = (ViewHolder) convertView.getTag();
		} else {
			viewholder = new ViewHolder();
			convertView = View.inflate(context, R.layout.adapter_process_manager, null);
			viewholder.iv_apk_icon = (ImageView) convertView.findViewById(R.id.iv_process_apk_icon);
			viewholder.tv_apk_name = (TextView) convertView.findViewById(R.id.tv_process_apk_name);
			viewholder.tv_apk_size = (TextView) convertView.findViewById(R.id.tv_process_apk_size);
			viewholder.iv_process_is_check = (ImageView) convertView.findViewById(R.id.iv_process_is_check);
			viewholder.rl_process_adapter = (RelativeLayout) convertView.findViewById(R.id.rl_process_adapter);
			
			convertView.setTag(viewholder);

		}
		
		 * boolean ischoice = false; if (!ischoice) {
		 * viewholder.iv_process_is_check.setEnabled(false); } else {
		 * viewholder.iv_process_is_check.setEnabled(true); }
		 
		
		 * if (bean_apk_info.isIscheck() == false) {
		 * viewholder.iv_process_is_check.setEnabled(false); } else {
		 * viewholder.iv_process_is_check.setEnabled(true); }
		 
		
		 * viewholder.rl_process_adapter.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { if (!ischoice) {
		 * viewholder.iv_process_is_check.setEnabled(true); } else {
		 * viewholder.iv_process_is_check.setEnabled(false); } ischoice =
		 * !ischoice; } });
		 
		boolean ischeck = bean_apk_info.isIscheck();
	
		if (ischeck) {
			viewholder.iv_process_is_check.setEnabled(true);
		} else {
			viewholder.iv_process_is_check.setEnabled(false);
		}
		viewholder.iv_apk_icon.setBackground(bean_apk_info.getPackageIcon());
		viewholder.tv_apk_name.setText(bean_apk_info.getPackageName().toString());
		viewholder.tv_apk_size.setText("占用内存:" + bean_apk_info.getPackageSize());
		return convertView;
	}

	public static class ViewHolder {
		public RelativeLayout rl_process_adapter;
		public ImageView iv_process_is_check;
		public ImageView iv_apk_icon;
		public TextView tv_apk_name;
		public TextView tv_apk_size;

	}

}
*/