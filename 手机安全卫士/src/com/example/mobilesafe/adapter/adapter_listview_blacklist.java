package com.example.mobilesafe.adapter;

import java.util.ArrayList;

import com.example.mobilesafe.R;
import com.example.mobilesafe.bean.Bean_blacklist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class adapter_listview_blacklist extends BaseAdapter {

	private ArrayList<Bean_blacklist> blacklist;
	private Context context;

	public adapter_listview_blacklist(Context context,ArrayList<Bean_blacklist> blacklist) {
		this.context = context;
		this.blacklist = blacklist;
		
	}
	@Override
	public int getCount() {
		return blacklist.size();
	}

	@Override
	public Bean_blacklist getItem(int position) {
		return blacklist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder;
		if(convertView == null){
			viewholder = new ViewHolder();
			convertView = View.inflate(context, R.layout.adapter_blacklist, null);
			viewholder.tv_black_number = (TextView) convertView.findViewById(R.id.tv_blacklist_number);
			viewholder.tv_black_name = (TextView) convertView.findViewById(R.id.tv_blacklist_name);
			viewholder.tv_black_status = (TextView) convertView.findViewById(R.id.tv_blacklist_status);
			convertView.setTag(viewholder);
		}else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		
		viewholder.tv_black_number.setText(blacklist.get(position).getNumber().toString());
		viewholder.tv_black_name.setText(blacklist.get(position).getName().toString());
		String status = blacklist.get(position).getStatus().toString();
		if(status.equals("0")){
			viewholder.tv_black_status.setText("拦截短信");
		}else if(status.equals("1")){
			viewholder.tv_black_status.setText("电话");
		}else {
			viewholder.tv_black_status.setText("拦截短信和电话");
		}
		
		return convertView;
	}
	
	class ViewHolder{
		TextView tv_black_number;
		TextView tv_black_name;
		TextView tv_black_status;
		
	}

}
