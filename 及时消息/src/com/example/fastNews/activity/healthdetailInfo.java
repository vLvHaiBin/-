package com.example.fastNews.activity;

import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;

import com.example.fastNews.R;
import com.example.fastNews.Utils.Utils;
import com.example.fastNews.bean.healthdetailInfo.resultInfo;
import com.example.fastNews.url.url;
import com.google.gson.Gson;
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
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class healthdetailInfo extends Activity {
	private ListView lv_detail;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		initData();
	}

	@SuppressLint("NewApi")
	private void initView() {
		setContentView(R.layout.healthdetailinfo);
		lv_detail = (ListView) findViewById(R.id.lv_healthdetailInfo);
		lv_detail.setDivider(getDrawable(com.example.fastNews.R.drawable.mz_card_list_divider_shade_light));
		lv_detail.setDividerHeight(Utils.getscreendensity(this, 8));
		id = getIntent().getStringExtra("id");
	}

	private void initData() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url.HEALTH_DETAIL + "id=" + id + "&" + url.KEY, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				System.out.println(result);
				Gson gson = new Gson();
				com.example.fastNews.bean.healthdetailInfo fromJson = gson.fromJson(result,
						com.example.fastNews.bean.healthdetailInfo.class);
				ArrayList<resultInfo> mResult = fromJson.result.data;
				MyAdapter adapter = new MyAdapter(mResult);
				lv_detail.setAdapter(adapter);
			}
		});
	}

	class MyAdapter extends BaseAdapter {

		private ArrayList<resultInfo> arrayList;
		private BitmapUtils bitmapUtils;

		public MyAdapter(ArrayList<resultInfo> arrayList) {
			bitmapUtils = new BitmapUtils(healthdetailInfo.this);
			this.arrayList = arrayList;
		}

		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = View.inflate(healthdetailInfo.this, R.layout.item_healthdetailinfo, null);
				viewHolder = new ViewHolder();
				viewHolder.tv_titile = (TextView) convertView.findViewById(R.id.tv_healthdetail_title);
				viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_healthdetailinfo_image);
				viewHolder.tv_maoshu = (TextView) convertView.findViewById(R.id.tv_healthdetailinfo_description);
				viewHolder.tv_kinds = (TextView) convertView.findViewById(R.id.tv_healthdetailinfo_keywords);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.tv_titile.setText(arrayList.get(position).title);
			bitmapUtils.display(viewHolder.iv_image, arrayList.get(position).img);
			viewHolder.tv_kinds.setText("¹Ø¼ü´Ê:" + arrayList.get(position).keywords);
			viewHolder.tv_maoshu.setText("    " + arrayList.get(position).description);
			return convertView;
		}

	}

	static class ViewHolder {
		TextView tv_titile;
		TextView tv_maoshu;
		TextView tv_kinds;
		ImageView iv_image;
	}

}
