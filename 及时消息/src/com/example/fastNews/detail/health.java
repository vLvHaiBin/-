package com.example.fastNews.detail;

import java.util.ArrayList;

import com.example.fastNews.R;
import com.example.fastNews.Utils.Utils;
import com.example.fastNews.activity.healthdetailInfo;
import com.example.fastNews.bean.healthTitle;
import com.example.fastNews.bean.healthTitle.healthList;
import com.example.fastNews.url.url;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class health extends BaseDetailpager {

	private ListView lv_health;

	public health(Activity activity) {
		super(activity);

	}

	@Override
	protected void initView() {
		v = View.inflate(activity, R.layout.health_layout, null);
		lv_health = (ListView) v.findViewById(R.id.lv_health);

	}

	@Override
	public void initdata() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url.HEALTH, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Utils.toast(activity, arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				Gson gson = new Gson();
				final healthTitle fromJson = gson.fromJson(result, healthTitle.class);
				System.out.println("½¡¿µ++++" + result);
				lv_health.setAdapter(new healthAdapter(fromJson.getHealth()));
				lv_health.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = new Intent(activity, healthdetailInfo.class);
						intent.putExtra("id", fromJson.getHealth().get(position).id);
						activity.startActivity(intent);

					}
				});

			}
		});

	}

	class healthAdapter extends BaseAdapter {

		private ArrayList<healthList> arrayList;

		public healthAdapter(ArrayList<healthList> arrayList) {
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
			TextView tv = new TextView(activity);
			tv.setText(arrayList.get(position).name);
			tv.setGravity(Gravity.CENTER);
			tv.setPadding(0, 30, 0, 30);
			tv.setTextSize(20);
			return tv;
		}

	}
}
