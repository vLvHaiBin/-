package com.example.mobilesafe.server;

import com.example.mobilesafe.R;
import com.example.mobilesafe.Utils.Utils;
import com.example.mobilesafe.activity.rocket_activity;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author like
 *火箭服务
 */
public class rocketserver extends Service {

	private WindowManager manager;
	private ImageView iv_socket;
	private WindowManager.LayoutParams params;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			int y = (Integer) msg.obj;
			params.y = y;
			manager.updateViewLayout(iv_socket, params);
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		final int width = manager.getDefaultDisplay().getWidth();
		final int height = manager.getDefaultDisplay().getHeight();
		params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		/*
		 * params.windowAnimations =
		 * com.android.internal.R.style.Animation_Toast;
		 */
		params.type = WindowManager.LayoutParams.TYPE_TOAST;// 不同的属性在界面上优先级和显示效果不一样
		params.setTitle("Toast");
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		/* | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE */;
		params.gravity = Gravity.LEFT + Gravity.TOP;// 设置toast的位置
		iv_socket = new ImageView(this);
		iv_socket.setBackgroundResource(R.drawable.anim_rocket);
		AnimationDrawable background = (AnimationDrawable) iv_socket.getBackground();
		background.start();
		manager.addView(iv_socket, params);
		iv_socket.setOnTouchListener(new OnTouchListener() {
			private int startX;
			private int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;

				case MotionEvent.ACTION_MOVE:
					int moveX = (int) event.getRawX();
					int moveY = (int) event.getRawY();

					int endX = moveX - startX;
					int endY = moveY - startY;

					params.x += endX;
					params.y += endY;
					if (params.x > width - iv_socket.getWidth()) {
						params.x = width - iv_socket.getWidth();
					} else if (params.x < 0) {
						params.x = 0;
					}
					if (params.y > height - iv_socket.getHeight() - 70) {
						params.y = height - iv_socket.getHeight() - 70;
					} else if (params.y < 0) {
						params.y = 0;
					}
					startX = moveX;
					startY = moveY;
					manager.updateViewLayout(iv_socket, params);// 更新位置
					break;

				case MotionEvent.ACTION_UP:
					/*
					 * handler.post(new Runnable() {
					 * 
					 * @Override public void run() { if(params.y
					 * >Utils.getscreendensity(rocketserver.this, 400) ) {
					 * for(int i =0;i<11;i++){ try { Thread.sleep(200); params.y
					 * =Utils.getscreendensity(rocketserver.this, 440 - i*44);
					 * 
					 * Utils.toast(rocketserver.this,""+ params.y);
					 * 
					 * } catch (InterruptedException e) { e.printStackTrace();
					 * }finally { manager.updateViewLayout(iv_socket, params); }
					 * 
					 * } } } });
					 */
					new Thread(new Runnable() {
						public void run() {
							Looper.prepare();
							
							if (params.y > Utils.getscreendensity(rocketserver.this, 400)) {
								Intent intent = new Intent(rocketserver.this,rocket_activity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
								for (int i = 0; i < 40; i++) {
									try {
										Thread.sleep(10);
										int y = Utils.getscreendensity(rocketserver.this, 440 - i * 11);
										Message message = handler.obtainMessage();
										message.obj = y;
										handler.sendMessage(message);
										Utils.toast(rocketserver.this, "" + params.y);

									} catch (InterruptedException e) {
										e.printStackTrace();
									} finally {

									}

								}
							}
						}
					}).start();
					;

					break;
				}
				return false;
			}
		});

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
