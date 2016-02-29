package com.example.fastNews.detail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.example.fastNews.R;
import com.example.fastNews.Utils.Utils;
import com.example.fastNews.Utils.toastUtils;
import com.example.fastNews.activity.hotNewsInfo;
import com.example.fastNews.bean.hotNews;
import com.example.fastNews.bean.hotNewsDetail;
import com.example.fastNews.bean.hotNewsDetail.mResult;
import com.example.fastNews.url.url;
import com.example.fastNews.viewGroup.RefreshListView.OnRefreshListener;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class detailpager extends BaseDetailpager {

	private PullToRefreshListView pullToRefreshListView;
	private ILoadingLayout startLayoutProxy;

	private FrameLayout frameLayout;

	private Handler mHandler;
	private ViewPager vp_pager;
	private LinearLayout ll_linear;
	private int[] image1s;
	private String[] text1s;
	private ListView lv;

	public detailpager(Activity activity) {
		super(activity);

	}

	@Override
	public void initView() {
		image1s = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
		text1s = new String[] { "巩俐不低俗，我就不低俗", "我爱玩大咖", "东风吹过春满地", "送大礼", "屌丝的逆袭" };

		v = View.inflate(activity, R.layout.detail_pager, null);
		View vv = View.inflate(activity, R.layout.viewpager, null);
		vp_pager = (ViewPager) vv.findViewById(R.id.vp_viewpager);
		ll_linear = (LinearLayout) vv.findViewById(R.id.ll_linear);
		pullToRefreshListView = (PullToRefreshListView) v.findViewById(R.id.pullToRefresh);
		pullToRefreshListView.setMode(Mode.BOTH);

		creatO();
		vplistener();
		viewpagerScroll();
		vp_pager.setAdapter(new MyViewPagerAdapter());

		lv = pullToRefreshListView.getRefreshableView();
		lv.addHeaderView(vv);

		startLayoutProxy = pullToRefreshListView.getLoadingLayoutProxy(true, false);
		startLayoutProxy.setRefreshingLabel("正在刷新");
		startLayoutProxy.setReleaseLabel("松开刷新");
		startLayoutProxy.setPullLabel("下拉刷新");
		ILoadingLayout endLayoutProxy = pullToRefreshListView.getLoadingLayoutProxy(false, true);
		endLayoutProxy.setRefreshingLabel("上拉刷新中。。。");
		endLayoutProxy.setReleaseLabel("上拉释放刷新");
		endLayoutProxy.setPullLabel("上拉刷新");

		pullToRefreshListView
				.setOnRefreshListener(new com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2() {

					@Override
					public void onPullDownToRefresh(PullToRefreshBase refreshView) {

						parseJson();
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						parseJson();
					}
				});
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position > lv.getHeaderViewsCount() - 1) {
					Intent intent = new Intent(activity, hotNewsInfo.class);

					intent.putExtra("title", result2s[position-2]);
					activity.startActivity(intent);
				}

			}
		});

		frameLayout = (FrameLayout) v.findViewById(R.id.fr_news);
	}

	@Override
	public void initdata() {

		parseJson();
		/*
		 * String json = Utils.sharprefrence_get(activity, "json", ""); if
		 * (TextUtils.isEmpty(json)) { parseJson(false, 0); } else { Gson gson =
		 * new Gson(); fromJson = gson.fromJson(json, hotNews.class); String[]
		 * result = fromJson.result; frameLayout.setVisibility(View.GONE);
		 * pullToRefreshListView.setAdapter(adapter); }
		 */

	}

	/**
	 * 
	 */
	private void parseJson() {

		HttpUtils httpUtils = new HttpUtils();

		httpUtils.send(HttpMethod.GET, url.NEWSURL, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				toastUtils.toast(activity, "新闻加载失败");
				pullToRefreshListView.onRefreshComplete();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				final String result = (String) arg0.result;
				Utils.sharprefrence_save(activity, "json", result);
				Gson gson = new Gson();
				hotNews fromJson = gson.fromJson(result, hotNews.class);

				getNewsInfo(result);

				System.out.println(result);

				frameLayout.setVisibility(View.GONE);

				/* pullToRefreshListView.setAdapter(adapter); */

			}
		});

	}

	protected void getNewsInfo(String result) {

		Gson gson = new Gson();
		hotNews fromJson = gson.fromJson(result, hotNews.class);
		result2s = fromJson.result;
		adapter = new MyAdapter(result2s);
		pullToRefreshListView.setAdapter(adapter);
		handler.sendEmptyMessageDelayed(0, 2000);

	}

	class MyAdapter extends BaseAdapter {

		private String[] sss;

		public MyAdapter(String[] sss) {
			this.sss = sss;
		}

		@Override
		public int getCount() {
			return sss.length;
		}

		@Override
		public String getItem(int position) {
			return sss[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("SimpleDateFormat")

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			/*
			 * ViewHolder viewHolder; if (convertView == null) { viewHolder =
			 * new ViewHolder(); convertView = View.inflate(activity,
			 * R.layout.item_detail_pager, null); viewHolder.tv_title =
			 * (TextView) convertView.findViewById(R.id.tv_title);
			 * viewHolder.tv_resource = (TextView)
			 * convertView.findViewById(R.id.tv_resource); viewHolder.tv_comment
			 * = (TextView) convertView.findViewById(R.id.tv_comment);
			 * viewHolder.tv_time = (TextView)
			 * convertView.findViewById(R.id.tv_time);
			 * convertView.setTag(viewHolder); } else { viewHolder =
			 * (ViewHolder) convertView.getTag(); }
			 * 
			 * String news_id = Utils.sharprefrence_get(activity, "news_id",
			 * ""); if (news_id.contains(arrayList.get(position).group_id)) {
			 * viewHolder.tv_title.setTextColor(Color.GRAY); }
			 * viewHolder.tv_title.setText(arrayList.get(position).title);
			 * viewHolder.tv_resource.setText(arrayList.get(position).source);
			 * viewHolder.tv_comment.setText(arrayList.get(position).digg_count
			 * + "评论"); String time = arrayList.get(position).behot_time; long
			 * valueOf = Long.valueOf(time);
			 * 
			 * SimpleDateFormat dateFormat = new SimpleDateFormat(
			 * "yyyy-MM-dd HH:mm:ss"); String format = dateFormat.format(new
			 * Date(valueOf));
			 * 
			 * viewHolder.tv_time.setText(format);
			 */

			TextView tv = new TextView(activity);
			tv.setText(sss[position]);
			tv.setGravity(Gravity.CENTER);
			tv.setPadding(0, 20, 0, 20);
			tv.setTextSize(20);
			return tv;
		}

	}

	class ViewHolder {
		TextView tv_title;
		TextView tv_resource;
		TextView tv_comment;
		TextView tv_time;
	}

	// 设置viewpager循环滚动

	private void viewpagerScroll() {
		vp_pager.setCurrentItem(5000);
		if (mHandler == null) {
			mHandler = new Handler() {

				@Override
				public void handleMessage(Message msg) {
					int current = vp_pager.getCurrentItem();
					if (current < 10000) {
						current++;
					} else {
						current = 0;
					}

					vp_pager.setCurrentItem(current);
					mHandler.sendEmptyMessageDelayed(0, 3000);
				}

			};
			mHandler.sendEmptyMessageDelayed(0, 3000);
		}

	}

	// 根据Viewpager的页面数量创建小圆点的数量

	public void creatO() {
		for (int i = 0; i < image1s.length; i++) {
			View v = new View(activity);
			LayoutParams layoutParams = new LayoutParams(15, 15);
			layoutParams.leftMargin = 10;
			v.setLayoutParams(layoutParams);
			v.setBackgroundResource(R.drawable.selector_oval);
			ll_linear.addView(v);
		}
	}

	// 设置Viewpger的适配器

	class MyViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 10000;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v = View.inflate(activity, R.layout.view_pager_item, null);
			TextView tv_title = (TextView) v.findViewById(R.id.tv_title);
			ImageView iv_image = (ImageView) v.findViewById(R.id.iv_imageview);
			tv_title.setText(text1s[position % text1s.length]);
			iv_image.setImageResource(image1s[position % text1s.length]);
			container.addView(v);
			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	@SuppressWarnings("deprecation")
	public void vplistener() {
		for (int j = 0; j < 5; j++) {
			if (j == 0) {
				ll_linear.getChildAt(j).setEnabled(true);
			} else {
				ll_linear.getChildAt(j).setEnabled(false);
			}
		}
		vp_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				for (int j = 0; j < 5; j++) {
					if (j == arg0 % text1s.length) {
						ll_linear.getChildAt(j).setEnabled(true);
					} else {
						ll_linear.getChildAt(j).setEnabled(false);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			adapter.notifyDataSetChanged();
			pullToRefreshListView.onRefreshComplete();
		};
	};
	private MyAdapter adapter;
	private String[] result2s;
}
