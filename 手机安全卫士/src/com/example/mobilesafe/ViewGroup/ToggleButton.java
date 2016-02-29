package com.example.mobilesafe.ViewGroup;

import com.example.mobilesafe.R;

import android.app.usage.UsageEvents.Event;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ToggleButton extends View {
	public static final int CLOSE = 0;
	public static final int OPEN = 1;
	private Bitmap slidebg;
	private Bitmap switchbg;
	private int state ;
	private int currentX;
	private Canvas mCanvas;
	private boolean isSliding;

	public ToggleButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	/**
	 * 
	 * 设置滑动块的背景图片
	 * 
	 * @param b
	 */
	public void setSlideBackgroundResource(int b) {
		slidebg = BitmapFactory.decodeResource(getResources(), b);
		
	}

	/**
	 * 设置开关的背景图片
	 * 
	 */
	public void setSwitchBackgroundResource(int a) {
		switchbg = BitmapFactory.decodeResource(getResources(), a);
	}

	/**
	 * 
	 * 设置开关的状态
	 * 
	 * @param open
	 *            
	 * @return
	 */
	public void setToggleState(int open) {
		state = open;
		state2 = open;
		invalidate();                  
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(switchbg.getWidth(), switchbg.getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		canvas.drawBitmap(switchbg, 0, 0, null);//必须使用bitmap
		if (isSliding) {
			int left = currentX - slidebg.getWidth() / 2;
			if (left < 0) {
				left = 0;
				canvas.drawBitmap(slidebg, left, 0, null);
			}
			if (left > switchbg.getWidth() - slidebg.getWidth()) {
				left = switchbg.getWidth() - slidebg.getWidth();
				canvas.drawBitmap(slidebg, left, 0, null);
			}
			canvas.drawBitmap(slidebg, left, 0, null);
		} else {
			if (state == ToggleButton.OPEN) {
				canvas.drawBitmap(slidebg, switchbg.getWidth() - slidebg.getWidth(), 0, null);
			} else {
				canvas.drawBitmap(slidebg, 0, 0, null);
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		currentX = (int) event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isSliding = true;
			break;
		case MotionEvent.ACTION_MOVE:

			break;

		case MotionEvent.ACTION_UP:
			isSliding = false;
			int centerX = switchbg.getWidth() / 2;
			if (currentX > centerX) {  
				if(state2 == ToggleButton.CLOSE){
				setToggleState(ToggleButton.OPEN);
				state2 = ToggleButton.OPEN;
				if(mChangeListener != null) {
					mChangeListener.onToggleStateChange(state2);
				}
				}
			} else {
				if(state2 == ToggleButton.OPEN){
				setToggleState(ToggleButton.CLOSE);
				state2 = ToggleButton.CLOSE;
				if(mChangeListener != null) {
					mChangeListener.onToggleStateChange(state2);
				}
				}
			}
			
			
			break;
		}
		invalidate();// 系统调用ondraw方法
		return true;
	}
	
	public interface ToggleStateChangeListener{
		 void onToggleStateChange(int state);
	};
	public ToggleStateChangeListener mChangeListener;
	private int state2 = ToggleButton.CLOSE;
	public void setOnToaggleStateChange(ToggleStateChangeListener mListener) {
		mChangeListener = mListener;
	}
	
}
