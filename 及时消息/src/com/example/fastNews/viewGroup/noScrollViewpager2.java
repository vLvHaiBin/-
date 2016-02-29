package com.example.fastNews.viewGroup;

import android.content.Context;
import android.support.v4.view.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;

public class noScrollViewpager2 extends ViewPager {

	private int startX;
	private int startY;
	private int current = getCurrentItem();
	public static String namespace = "http://schemas.android.com/apk/res-auto";
	private String size;
	private int msize;
	public noScrollViewpager2(Context context, AttributeSet attrs) {
		super(context, attrs);
		size = attrs.getAttributeValue(namespace, "size");
		msize = Integer.valueOf(size);
	}

	public noScrollViewpager2(Context context) {
		super(context);
	}
	

@Override
public boolean onTouchEvent(MotionEvent arg0) {
	System.out.println(""+getChildCount());
	return super.onTouchEvent(arg0);
}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		
		if(getCurrentItem() == msize){
			getParent().requestDisallowInterceptTouchEvent(false);
		}else {
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		return super.dispatchTouchEvent(ev);
	}
	
	public void setSize(int size){
		if(size != -1){
			 msize = size;
		}
		
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(arg0);
	}

}
