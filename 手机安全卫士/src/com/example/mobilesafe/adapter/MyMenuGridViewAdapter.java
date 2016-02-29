package com.example.mobilesafe.adapter;


import com.example.mobilesafe.R;

import android.R.layout;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyMenuGridViewAdapter extends BaseAdapter {
		
	 
	private int[] image;
	private String[] text;
	private Context context;


	public MyMenuGridViewAdapter(int[] image,String[] text,Context context) {
		this.image = image;
		this.text = text;
		this.context = context;
		
		
	}
	@Override
	public int getCount() {
		return image.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_gridview_list, null);
		TextView tv_menu_text = (TextView) convertView.findViewById(R.id.tv_menu_text);
		ImageView iv_menu_image = (ImageView) convertView.findViewById(R.id.iv_menu_image);
		tv_menu_text.setText(text[position]);
		iv_menu_image.setImageResource(image[position]);
		return convertView;
	}

}
