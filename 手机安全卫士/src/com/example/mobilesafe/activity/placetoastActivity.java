package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author like
 *归属地显示位置设置
 */
public class placetoastActivity extends Activity {
	long[] mHits = new long[2];
	private TextView tv_top;
	private ImageView iv_drag;
	private RelativeLayout rl_drag;
	private int width;
	private int heigh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_toast);
		initView();
		initdata();
		
	}

	private void initView() {
		tv_top = (TextView) findViewById(R.id.tv_top);
		iv_drag = (ImageView) findViewById(R.id.iv_drag);
		iv_drag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Utils.toast(placetoastActivity.this, "双击");
				System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
				mHits[mHits.length - 1] = SystemClock.uptimeMillis();// 开机后开始计算的时间
				if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
					// 把图片居中
					iv_drag.layout(width / 2 - iv_drag.getWidth() / 2,
							heigh/2-iv_drag.getHeight()/2, width / 2 + iv_drag.getWidth()
									/ 2, heigh/2+iv_drag.getHeight()/2);
					Utils.sharprefrence_saveInt(placetoastActivity.this, "lastX", iv_drag.getLeft());
					Utils.sharprefrence_saveInt(placetoastActivity.this, "lastY", iv_drag.getTop());
				}				
			}
		});
		rl_drag = (RelativeLayout) findViewById(R.id.rl_drag);
	}

	private void initdata() {

		width = getWindowManager().getDefaultDisplay().getWidth();
		heigh = getWindowManager().getDefaultDisplay().getHeight();
		int x= Utils.sharprefrence_getInt(this, "lastX", -1);
		int y = Utils.sharprefrence_getInt(this, "lastY", -1);
		if(x != -1 && y!= -1){//每次打开页面时，设置View的位置
		
			 RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) 
					 iv_drag.getLayoutParams();
			 
			 params.leftMargin = x;
			 params.topMargin = y;
			 iv_drag.setLayoutParams(params);
		}
	
		iv_drag.setOnTouchListener(new OnTouchListener() {
			
			private int startX;
			private int startY;
			private int moveX;
			private int moveY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// 初始化起点坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					int endX = (int) event.getRawX();
					int endY = (int) event.getRawY();

					// 计算移动偏移量
					int dx = endX - startX;
					int dy = endY - startY;

					// 更新左上右下距离
					int l = iv_drag.getLeft() + dx;
					int r = iv_drag.getRight() + dx;

					int t = iv_drag.getTop() + dy;
					int b = iv_drag.getBottom() + dy;
					if(l<0 || r > width || t<0 || b>heigh - 70){
						break;
					}
					// 更新界面
					iv_drag.layout(l, t, r, b);
					/* RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) 
							 iv_drag.getLayoutParams();
					 params.leftMargin = endX;
					 params.topMargin = endY;
					 iv_drag.setLayoutParams(params);*/

					// 重新初始化起点坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					// 记录坐标点
				Utils.sharprefrence_saveInt(placetoastActivity.this, "lastX", iv_drag.getLeft());
				Utils.sharprefrence_saveInt(placetoastActivity.this, "lastY", iv_drag.getTop());
					break;
				}

				return false;
			}
		});
	}
}
