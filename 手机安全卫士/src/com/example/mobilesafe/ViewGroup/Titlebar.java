package com.example.mobilesafe.ViewGroup;

import com.example.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Titlebar extends LinearLayout {

	private Context context;
	private static final String NAMESPACE ="http://schemas.android.com/apk/res/com.example.mobilesafe";
	public  ToggleButton toggleButton;
	private String title;
	public ImageView iv_back;
	public Titlebar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public Titlebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		title = attrs.getAttributeValue(NAMESPACE, "title");
		initView();
	}

	public Titlebar(Context context) {
		super(context);
		this.context = context;
		initView();
	}
	
	private void initView() {
		View v = View.inflate(getContext(), R.layout.zidingyi_titile, this);
		iv_back = (ImageView) v.findViewById(R.id.iv_back);
		TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText(title);
		
	}



}
