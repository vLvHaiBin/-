package com.example.fastNews.fragment;

import java.util.ArrayList;

import com.example.fastNews.R;
import com.example.fastNews.base.basePager;
import com.example.fastNews.base.newpager;
import com.example.fastNews.base.carPager;
import com.example.fastNews.base.readpager;
import com.example.fastNews.viewGroup.noScrollViewpager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author like 主界面的fragment，此界面才是在activity上依附的。数据最后都在这儿改变
 */
public class contentFragment extends BaseFragment implements OnClickListener {

	private LinearLayout ll_home;
	private LinearLayout ll_video;
	private LinearLayout ll_setting;
	private noScrollViewpager vp_content;
	private ArrayList<basePager> pagerlist = null;
	private ImageView iv_news;
	private ImageView iv_read;
	private ImageView iv_care;
	private TextView tv_news;
	private TextView tv_read;
	private TextView tv_care;

	@Override
	public View initVeiw() {
		View v = View.inflate(mActivity, R.layout.fragment_content, null);
		ll_home = (LinearLayout) v.findViewById(R.id.ll_home);
		ll_home.setOnClickListener(this);
		ll_video = (LinearLayout) v.findViewById(R.id.ll_video);
		ll_video.setOnClickListener(this);
		ll_setting = (LinearLayout) v.findViewById(R.id.ll_setting);
		ll_setting.setOnClickListener(this);
		vp_content = (noScrollViewpager) v.findViewById(R.id.vp_content);
		iv_news = (ImageView) v.findViewById(R.id.iv_news);
		iv_read = (ImageView) v.findViewById(R.id.iv_read);
		iv_care = (ImageView) v.findViewById(R.id.iv_care);
		iv_news.setEnabled(true);
		iv_care.setEnabled(false);
		iv_read.setEnabled(false);
		tv_news = (TextView) v.findViewById(R.id.tv_news);
		tv_read = (TextView) v.findViewById(R.id.tv_read);
		tv_care = (TextView) v.findViewById(R.id.tv_care);
		tv_news.setEnabled(true);
		tv_read.setEnabled(false);
		tv_care.setEnabled(false);
		return v;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void initData() {
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				pagerlist.get(arg0).initData();//
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		pagerlist = new ArrayList<basePager>();

		newpager mNewpager = new newpager(mActivity);
		readpager mVideopager = new readpager(mActivity);
		carPager mSettingpager = new carPager(mActivity);

		pagerlist.add(mNewpager);
		pagerlist.add(mVideopager);
		pagerlist.add(mSettingpager);

		MyViewpager myViewpager = new MyViewpager();
		vp_content.setAdapter(myViewpager);
		pagerlist.get(0).initData();// 处理逻辑，加载数据，默认第一页

	}

	/**
	 * @author like viewpager适配器
	 */
	class MyViewpager extends PagerAdapter {

		@Override
		public int getCount() {
			return pagerlist.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pagerlist.get(position).v);
			return pagerlist.get(position).v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_home:

			setFocusable(R.id.ll_home);
			vp_content.setCurrentItem(0, false);
			break;

		case R.id.ll_video:
			setFocusable(R.id.ll_video);
			vp_content.setCurrentItem(1, false);
			break;

		case R.id.ll_setting:
			setFocusable(R.id.ll_setting);
			vp_content.setCurrentItem(2, false);
			break;

		}
	}

	public void setFocusable(int id) {
		if (id == R.id.ll_home) {
			iv_news.setEnabled(true);
			iv_care.setEnabled(false);
			iv_read.setEnabled(false);
			tv_news.setEnabled(true);
			tv_read.setEnabled(false);
			tv_care.setEnabled(false);
		} else if (id == R.id.ll_video) {
			iv_news.setEnabled(false);
			iv_care.setEnabled(false);
			iv_read.setEnabled(true);

			tv_news.setEnabled(false);
			tv_read.setEnabled(true);
			tv_care.setEnabled(false);
		} else {
			iv_news.setEnabled(false);
			iv_care.setEnabled(true);
			iv_read.setEnabled(false);

			tv_news.setEnabled(false);
			tv_read.setEnabled(false);
			tv_care.setEnabled(true);
		}

	}

}
