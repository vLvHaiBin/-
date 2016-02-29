package com.example.fastNews.viewGroup;

import android.content.Context;
import android.support.v4.view.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;

public class noScrollViewpager extends ViewPager {

	public noScrollViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public noScrollViewpager(Context context) {
		super(context);
	}
		
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
		
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
			/*getParent().requestDisallowInterceptTouchEvent(true);//让其他控件不拦截点击事件
*/		return super.dispatchTouchEvent(ev);
	}

	
	

}
