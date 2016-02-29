package com.example.fastNews.base;


import com.example.fastNews.R;
import com.example.fastNews.Utils.PagerSlidingTabStrip;
import com.example.fastNews.viewGroup.noScrollViewpager2;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author like
 *设置界面
 */
public class carPager extends basePager {

	private PagerSlidingTabStrip findViewById;
	private noScrollViewpager2 viewPager;
	private String[] titles;

	public carPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initView() {
		v = View.inflate(activity, R.layout.carpager, null);
		findViewById = (PagerSlidingTabStrip) v.findViewById(R.id.tabs_car);
		viewPager = (noScrollViewpager2) v.findViewById(R.id.vp_car);

	}

	@Override
	public void initData() {
		titles = new String[] {"热点", "订阅" };
		Mypager mypager = new Mypager();
		viewPager.setAdapter(mypager);
		findViewById.setViewPager(viewPager);
		viewPager.setSize(2);

	}

	class Mypager extends PagerAdapter {

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TextView tv = new TextView(activity);
			tv.setText(titles[position]);
			container.addView(tv);

			return tv;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}
}
