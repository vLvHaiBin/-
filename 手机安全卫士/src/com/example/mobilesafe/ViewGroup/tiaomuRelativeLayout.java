package com.example.mobilesafe.ViewGroup;

import com.example.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class tiaomuRelativeLayout extends RelativeLayout {
	private static final String NAMESPACE ="http://schemas.android.com/apk/res/com.example.mobilesafe";
	private String title;
	public tiaomuRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public tiaomuRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		title = attrs.getAttributeValue(NAMESPACE,"tiaomu_title");
		initView();
		
	}

	public tiaomuRelativeLayout(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		View v = View.inflate(getContext(), R.layout.zidingyi_tiaomu, this);
		TextView tv_content = (TextView) v.findViewById(R.id.tv_content);
		tv_content.setText(title);
	}

}
