package com.example.fastNews.viewGroup;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class mSwipRefresh extends SwipeRefreshLayout {

	private ViewGroup group;

	public mSwipRefresh(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public mSwipRefresh(Context context) {
		super(context);
	}

	public void setVeiwGroup(ViewGroup group) {
		this.group = group;

	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (group != null) {
			if (group.getScrollY() > 1) {
				return false;
			}
		}
		return super.onTouchEvent(arg0);
	}

}
