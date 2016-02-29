package com.example.fastNews.activity;

import java.util.ArrayList;

import com.example.fastNews.R;
import com.example.fastNews.R.drawable;
import com.example.fastNews.Utils.Utils;
import com.example.fastNews.bean.ComicBean;
import com.example.fastNews.bean.ComicBean.mBookList;
import com.example.fastNews.url.url;
import com.example.fastNews.viewGroup.mSwipRefresh;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ComicActivity extends Activity {

	private GridView gridView;
	private mSwipRefresh refre1shLayout;
	private String name;
	private MyAdapter adapter;
	private ComicBean fromJson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	@SuppressWarnings("deprecation")
	private void initView() {

		setContentView(R.layout.comicsecond);
		gridView = (GridView) findViewById(R.id.gv_comic);

		refre1shLayout = (mSwipRefresh) findViewById(R.id.srl_refresh);
		refre1shLayout.setVeiwGroup(gridView);
		refre1shLayout.setColorScheme(R.color.color_C231C7
				,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_dark);
		name = getIntent().getStringExtra("name");
		refre1shLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				HttpUtils httpUtils = new HttpUtils();
				httpUtils.send(HttpMethod.GET, url.COMIC + name, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;
						Gson gson = new Gson();
						fromJson = gson.fromJson(result, ComicBean.class);
						adapter.notifyDataSetChanged();
						
						refre1shLayout.setRefreshing(false);
						Utils.toast(ComicActivity.this, "Ë¢ÐÂ");

					}
				});
			}
		});
	}

	private void initData() {
		gridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem == 0 ) {
					refre1shLayout.setEnabled(true);
				} else {
					refre1shLayout.setEnabled(false);
				}
			}
		});
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url.COMIC + name, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				Gson gson = new Gson();
				fromJson = gson.fromJson(result, ComicBean.class);
				adapter = new MyAdapter(fromJson.result.bookList);
				gridView.setAdapter(adapter);

			}
		});
	}

	public void isRefresh() {
		boolean refreshing = refre1shLayout.isRefreshing();
		if (refreshing) {
			refre1shLayout.setVisibility(View.GONE);
		} else {
			refre1shLayout.setVisibility(View.VISIBLE);
		}
	}

	class MyAdapter extends BaseAdapter {

		private ArrayList<mBookList> arrayList;
		private BitmapUtils bitmapUtils;

		public MyAdapter(ArrayList<mBookList> arrayList) {
			this.arrayList = arrayList;
			bitmapUtils = new BitmapUtils(ComicActivity.this);
		}

		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return arrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = View.inflate(ComicActivity.this, R.layout.item_comic, null);
				viewHolder = new ViewHolder();
				viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_comic_image);
				viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_comic_name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			bitmapUtils.display(viewHolder.iv_image, arrayList.get(position).coverImg);
			viewHolder.tv_name.setText(arrayList.get(position).name);

			return convertView;
		}
	}

	static class ViewHolder {
		TextView tv_name;
		ImageView iv_image;
	}

}
