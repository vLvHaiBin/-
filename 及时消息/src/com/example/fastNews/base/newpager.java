package com.example.fastNews.base;

import java.util.ArrayList;

import com.example.fastNews.R;
import com.example.fastNews.Utils.PagerSlidingTabStrip;
import com.example.fastNews.Utils.Utils;
import com.example.fastNews.Utils.mStringArray;
import com.example.fastNews.activity.MainActivity;
import com.example.fastNews.bean.hotNewsDetail;
import com.example.fastNews.detail.BaseDetailpager;
import com.example.fastNews.detail.ComicPager;
import com.example.fastNews.detail.detailpager;
import com.example.fastNews.detail.health;
import com.example.fastNews.detail.jokedetailpager;
import com.example.fastNews.detail.menupager;
import com.example.fastNews.viewGroup.noScrollViewpager2;
import com.example.tuodong.DragGridView;
import com.example.tuodong.GridViewAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * @author like 信息界面
 */
public class newpager extends basePager {

	private PagerSlidingTabStrip tabStrip;
	private String[] tts;
	private noScrollViewpager2 vp_news;
	private hotNewsDetail fromJson;
	private detailpager detailpager;
	private jokedetailpager jokedetailpager;
	private ArrayList<BaseDetailpager> arrayList;
	private String[] titles;
	private ImageView iv_news;
	private int radius;
	private ImageView iv_news_add;
	private SlidingMenu slidingMenu;
	private ImageView iv_add_news;
	private PopupWindow popupWindow;
	private View vv;
	private health healthpager;
	private menupager menup;
	private ComicPager comicPager;

	public newpager(Activity activity) {
		super(activity);

	}

	@Override
	public void initView() {

		v = View.inflate(activity, R.layout.newspager, null);
		vp_news = (noScrollViewpager2) v.findViewById(R.id.vp_news);
		iv_add_news = (ImageView) v.findViewById(R.id.iv_news_add);
		tabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
		iv_news = (ImageView) v.findViewById(R.id.iv_news);
		iv_add_news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				colsePop();
				/*
				 * Intent intent = new Intent(activity,
				 * Activity_add_news.class); intent.putExtra("newslist",
				 * titles); activity.startActivityForResult(intent, 1);
				 * 
				 */
			}
		});
		iv_news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingMenu.toggle();
			}
		});
		iv_news_add = (ImageView) v.findViewById(R.id.iv_news_add);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void initData() {

		MainActivity mainActivity = (MainActivity) activity;
		slidingMenu = mainActivity.getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		drawCircle();
		titles = mStringArray.getInstance().title;
		tabStrip.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == tabStrip.getChildCount() - 1) {

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		arrayList = new ArrayList<BaseDetailpager>();
		super.initData();
		detailpager = new detailpager(activity);
		jokedetailpager = new jokedetailpager(activity);
		healthpager = new health(activity);
		menup = new menupager(activity);
		comicPager = new ComicPager(activity);
		arrayList.add(detailpager);
		arrayList.add(jokedetailpager);
		arrayList.add(healthpager);
		arrayList.add(menup);
		arrayList.add(comicPager);
		MyViewpager myViewpager = new MyViewpager();
		vp_news.setSize(arrayList.size());
		vp_news.setAdapter(myViewpager);
		tabStrip.setViewPager(vp_news);
		vp_news.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 < arrayList.size()) {
					arrayList.get(arg0).initdata();
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		;
		detailpager.initdata();

	}

	/**
	 * 将图形变成圆形
	 */
	private void drawCircle() {
		Bitmap decodeResource = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.mz_status_ic_notify_email_contact);
		Bitmap createBitmap = Bitmap.createBitmap(decodeResource.getHeight(), decodeResource.getWidth(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(createBitmap);
		Paint paint = new Paint();
		if (decodeResource.getWidth() >= decodeResource.getHeight()) {
			radius = decodeResource.getHeight() / 2;
		} else {
			radius = decodeResource.getWidth() / 2;
		}
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(decodeResource.getWidth() / 2, decodeResource.getHeight() / 2,
				Utils.getscreendensity(activity, 25), paint);
		paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(decodeResource, 0, 0, paint);
		iv_news.setImageBitmap(createBitmap);
	}

	class MyViewpager extends PagerAdapter {

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (position < arrayList.size()) {
				container.addView(arrayList.get(position).v);
				return arrayList.get(position).v;

			} else {
				TextView tv = new TextView(activity);
				tv.setText(titles[position]);
				container.addView(tv);
				return tv;
			}

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);

		}

	}

	@SuppressLint("NewApi")
	private void showPopWindows() {
		vv = View.inflate(activity, R.layout.activity_news_add, null);
		DragGridView dragGridView = (DragGridView) vv.findViewById(R.id.drag_add_news);
		ArrayList<String> xx = mStringArray.getInstance().arrayList;
		GridViewAdapter adapter = new GridViewAdapter(activity, xx);
		dragGridView.setAdapter(adapter);
		vv.clearAnimation();
		popupWindow = new PopupWindow(vv, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		popupWindow.setTouchable(true);
		popupWindow.showAsDropDown(iv_add_news, 0, 0);
		TranslateAnimation ani = new TranslateAnimation(0, 0, -1500, 0);
		ani.setDuration(400);
		ani.setFillAfter(true);
		vv.startAnimation(ani);

	}

	public void colsePop() {
		if (popupWindow != null) {
			if (popupWindow.isShowing()) {
				vv.clearAnimation();
				TranslateAnimation ani = new TranslateAnimation(0, 0, 0, -1700);
				ani.setDuration(500);
				vv.startAnimation(ani);

				ani.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						Utils.toast(activity, "动画结束");
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						Utils.toast(activity, "动画结束");
						animation.cancel();
						popupWindow.dismiss();
						popupWindow = null;

					}
				});

			}

		} else {
			showPopWindows();
		}
	}

}
