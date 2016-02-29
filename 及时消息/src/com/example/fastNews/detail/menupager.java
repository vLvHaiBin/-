package com.example.fastNews.detail;

import java.util.ArrayList;

import com.example.fastNews.R;
import com.example.fastNews.bean.caldanInfo.caidanDetail;
import com.example.fastNews.url.url;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class menupager extends BaseDetailpager {

	private ListView lv_menu;

	public menupager(Activity activity) {
		super(activity);
	}

	@Override
	protected void initView() {
		v = View.inflate(activity, R.layout.menu_caidan, null);
		lv_menu = (ListView) v.findViewById(R.id.lv_menu_caidan);

	}

	@Override
	public void initdata() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url.CAIDAN, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				Gson gson = new Gson();
				System.out.println(result);
				caidanDetail fromJson = gson.fromJson(result, caidanDetail.class);

			}
		});
	}

	class MyAdapter extends BaseAdapter {

		private ArrayList<caidanDetail> arrayList;

		public MyAdapter(ArrayList<caidanDetail> arrayList) {
			this.arrayList = arrayList;
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

			return null;
		}

	}
}
