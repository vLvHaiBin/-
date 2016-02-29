package com.example.mobilesafe.adapter;

import java.util.ArrayList;

import com.example.mobilesafe.R;
import com.example.mobilesafe.bean.Bean_apk_info;
import com.example.mobilesafe.bean.Bean_blacklist;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author like
 *app listadapter
 */
public class adapter_listview_sw_manager extends BaseAdapter {

	private ArrayList<Bean_apk_info> apk_infos;
	private Context context;
	public  ArrayList<Bean_apk_info> userApp;
	public  ArrayList<Bean_apk_info> systemApp;
	private Bean_apk_info bean_apk_info;
	
	
	public adapter_listview_sw_manager(Context context,final ArrayList<Bean_apk_info> apk_infos) {
		this.context = context;
		this.apk_infos = apk_infos;
		userApp = new ArrayList<Bean_apk_info>();
		systemApp = new ArrayList<Bean_apk_info>();
		
				for(Bean_apk_info bean_apk_info:apk_infos){
					if(bean_apk_info.isUser){//将用户和系统应用装入不同的集合
						userApp.add(bean_apk_info);
					}else {
						systemApp.add(bean_apk_info);
					}
				}
			}
		
		
	
	
	@Override
	public int getCount() {
		return userApp.size() +1 + systemApp.size() +1;
	}

	@Override
	public Bean_apk_info getItem(int position) {
		Bean_apk_info mbean_apk_info = null;	
		if(position == 0){
			return null;
		}else if(position == userApp.size()+1) {
			return null;
		}
		else if (position < userApp.size()+1){
			 mbean_apk_info = userApp.get(position-1); 
		}else if (position > userApp.size()+1) {
			mbean_apk_info = systemApp.get(position -(userApp.size()+2));
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
		ViewHolder viewholder;
		if(position == 0){
			TextView tv_user = new TextView(context);
			tv_user.setText("用户应用(" + userApp.size()+")");
			tv_user.setBackgroundColor(Color.GRAY);
			return tv_user;
		}else if (position ==(userApp.size()+1)) {
			TextView tv_system = new TextView(context);
			tv_system.setText("系统应用(" +systemApp.size()+")");
			tv_system.setBackgroundColor(Color.GRAY);
			return tv_system;
		
		}else if (position < userApp.size()+1){
			bean_apk_info = userApp.get(position-1); 
		}else if (position > userApp.size()+1) {
			bean_apk_info = systemApp.get(position -(userApp.size()+2));
		}
		
		
		if(convertView != null && convertView instanceof RelativeLayout){
			viewholder = (ViewHolder) convertView.getTag();
		}else {
			viewholder = new ViewHolder();
			convertView = View.inflate(context, R.layout.adapter_sw_manager, null);
			viewholder.iv_apk_icon = (ImageView) convertView.findViewById(R.id.iv_apk_icon);
			viewholder.tv_apk_name = (TextView) convertView.findViewById(R.id.tv_apk_name);
			viewholder.tv_apk_location = (TextView) convertView.findViewById(R.id.tv_apk_location);
			viewholder.tv_apk_size = (TextView) convertView.findViewById(R.id.tv_apk_size);
			convertView.setTag(viewholder);
			
		}
		      
		viewholder.iv_apk_icon.setBackground(bean_apk_info.getApkIcon());
		viewholder.tv_apk_name.setText(bean_apk_info.apkname.toString());
		viewholder.tv_apk_size.setText(bean_apk_info.getApksize());
		if(bean_apk_info.isRam){
			viewholder.tv_apk_location.setText("sd卡");
		}else {
			viewholder.tv_apk_location.setText("手机内存");
		}
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView iv_apk_icon;
		TextView tv_apk_name;
		TextView tv_apk_size;
		TextView tv_apk_location;
		
	}

}
