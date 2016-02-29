package com.example.fastNews.detail;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.fastNews.R;
import com.example.fastNews.Utils.toastUtils;
import com.example.fastNews.activity.hotNewsInfo;
import com.example.fastNews.bean.joke;
import com.example.fastNews.bean.joke.jokedetail;
import com.example.fastNews.bean.hotNewsDetail;

import com.example.fastNews.url.url;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author like –¶ª∞
 */
public class jokedetailpager extends BaseDetailpager {

	private PullToRefreshListView lv_detail;

	private FrameLayout frameLayout;
	private joke mjoke;

	public jokedetailpager(Activity activity) {
		super(activity);
	}

	@Override
	public void initView() {
		v = View.inflate(activity, R.layout.detail_pager, null);
		lv_detail = (PullToRefreshListView) v.findViewById(R.id.pullToRefresh);
		frameLayout = (FrameLayout) v.findViewById(R.id.fr_news);
	}

	@Override
	public void initdata() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url.JOKESURL, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				toastUtils.toast(activity, "–¶ª∞«Î«Û ß∞‹");
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = (String) arg0.result;
				getNewsInfo(result);
				System.out.println(result);

			}
		});

	};

	protected void getNewsInfo(String result) {
		Gson gson = new Gson();
		mjoke = gson.fromJson(result, joke.class);
		MyAdapter adapter = new MyAdapter();
		frameLayout.setVisibility(View.GONE);
		lv_detail.setAdapter(adapter);

		lv_detail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(activity, hotNewsInfo.class);
				intent.putExtra("url", mjoke.detail.get(position).content);
				activity.startActivity(intent);
			}
		});
	}

	class MyAdapter extends BaseAdapter {

		private BitmapUtils bitmapUtils;

		@SuppressLint("NewApi")
		public MyAdapter() {
			bitmapUtils = new BitmapUtils(activity);
			bitmapUtils.configDefaultLoadFailedImage(activity.getDrawable(com.example.fastNews.
					R.drawable.mz_scrubber_primary_white_disable));
		}

		@Override
		public int getCount() {
			return mjoke.detail.size();
		}

		@Override
		public jokedetail getItem(int position) {
			return mjoke.detail.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(activity, R.layout.item_joke_detail_pager, null);
				viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_joke_author);
				viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_joke_content);
				viewHolder.iv_joke_image = (ImageView) convertView.findViewById(R.id.iv_joke_image);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_title.setText(mjoke.detail.get(position).author);
			viewHolder.tv_content.setText(mjoke.detail.get(position).content);
			String imageurl = mjoke.detail.get(position).picUrl;
			if (TextUtils.isEmpty(imageurl)) {
				imageurl = "sdfsdgfsg";
			}

			bitmapUtils.display(viewHolder.iv_joke_image, mjoke.detail.get(position).picUrl);

			return convertView;

		}

	}

	class ViewHolder {
		TextView tv_title;
		ImageView iv_joke_image;
		TextView tv_content;
	}

}
