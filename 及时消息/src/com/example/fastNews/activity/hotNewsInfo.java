package com.example.fastNews.activity;

import java.util.ArrayList;

import com.example.fastNews.R;
import com.example.fastNews.Utils.Utils;
import com.example.fastNews.bean.hotNewsDetail;
import com.example.fastNews.bean.hotNewsDetail.mResult;
import com.example.fastNews.url.url;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class hotNewsInfo extends Activity {

	private ImageView iv_share;
	private ListView lv_list;
	private ImageView iv_back;
	private String title;
	private ArrayList<mResult> result2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		ShareSDK.initSDK(this);
		initView();
		initData();
		location();
	}

	private void initView() {

		setContentView(R.layout.hostnewsdetail);
		iv_back = (ImageView) findViewById(R.id.iv_nhotnews_back);
		lv_list = (ListView) findViewById(R.id.lv_hot_list);
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(hotNewsInfo.this, HotNewsContentActivity.class);
				intent.putExtra("url", result2.get(position).url);

				startActivity(intent);
			}
		});
		title = getIntent().getStringExtra("title");

	}

	private void initData() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url.HOTNES + title, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				String result = arg0.result;
				Gson gson = new Gson();
				hotNewsDetail fromJson = gson.fromJson(result, hotNewsDetail.class);
				result2 = fromJson.result;
				Myadapter myadapter = new Myadapter(result2);
				lv_list.setAdapter(myadapter);
			}
		});
	}

	class Myadapter extends BaseAdapter {

		private ArrayList<mResult> arrayList;
		@SuppressWarnings("unused")
		private BitmapUtils bitmapUtils;

		public Myadapter(ArrayList<mResult> arrayList) {
			this.arrayList = arrayList;
			bitmapUtils = new BitmapUtils(hotNewsInfo.this);
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
			convertView = View.inflate(hotNewsInfo.this, R.layout.item_detail_pager, null);
			ImageView iv_image = (ImageView) convertView.findViewById(R.id.iv_hot_news);
			TextView tv_title = (TextView) convertView.findViewById(R.id.tv_hot_title);
			TextView tv_content = (TextView) convertView.findViewById(R.id.tv_hot_content);
			TextView tv_source = (TextView) convertView.findViewById(R.id.tv_resource);
			TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);

			if (!arrayList.get(position).img.isEmpty()) {
				bitmapUtils.display(iv_image, arrayList.get(position).img);
			} else {
				iv_image.setVisibility(View.GONE);
			}

			tv_title.setText(arrayList.get(position).full_title);
			tv_content.setText(arrayList.get(position).content);
			tv_source.setText(arrayList.get(position).src);
			tv_time.setText(arrayList.get(position).pdate);

			return convertView;
		}

	}

	static class ViewHolder {
		ImageView iv_image;
		TextView tv_title;
		TextView tv_content;
		TextView tv_source;
		TextView tv_time;
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		oks.disableSSOWhenAuthorize();

		oks.setTitle("分享");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");
		// 启动分享GUI
		oks.show(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.initSDK(this);
	}

	public void location() {

		LocationManager lc = (LocationManager) getSystemService(LOCATION_SERVICE);
		ll lll = new ll();
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);// 设置是否可以用网络
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// 设置帅选条件，选high没用
		String bestProvider = lc.getBestProvider(criteria, true);// 自动选择定位的方法
		lc.requestLocationUpdates(bestProvider, 0, 0, lll);
	}

	class ll implements LocationListener {

		@Override
		public void onLocationChanged(android.location.Location location) {
			double j = location.getLongitude();// 经度
			double w = location.getLatitude();// 纬度
			float accuracy = location.getAccuracy();// 精确度
			double altitude = location.getAltitude();// 海拔高度
			Log.e("dizhi ", j+"sssssssss"+w);

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

	}

}
